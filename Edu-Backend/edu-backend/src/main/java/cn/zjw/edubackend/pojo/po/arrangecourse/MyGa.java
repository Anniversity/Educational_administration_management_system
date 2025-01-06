package cn.zjw.edubackend.pojo.po.arrangecourse;

import cn.zjw.edubackend.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 遗传算法排课
 *
 * @author Gaven
 * @date 2023/12/01 1:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyGa {

    //种群的规模（0-100）
    private Integer popsize = 32;
    //种群的变异概率
    private Double mutprob = 0.3;
    //精英种群的个数
    private Integer elite = 15;
    //进化代数（100-500）
    private Integer maxiter = 500;
    //所有的种群 每一个种群中存放需要编排的课程列表
    private List<List<GaSchedule>> population;
    // 初始化的种群 这部分的种群不能被改变 每次初始化都要添加进去
    private List<GaSchedule> initPopulation;

    /**
     * ga主体
     * 参数：教学任务信息列表 、所有可以使用的教师的id列表 、需要排到第几节课
     *
     * @param gaSchedules
     * @return
     */
    public List<GaSchedule> evolution(List<GaSchedule> gaSchedules) {
        //冲突最小的染色体的冲突得分，若为0则说明已获得最优的解，可返回
        int bestScore = 0;
        //最优的课程编排结果
        List<GaSchedule> bestGaSchedule = new ArrayList<>();
        init_population(gaSchedules);
        for (int i = 0; i < this.maxiter; i++) {
            List<List<GaSchedule>> newPopulation = new ArrayList<>();
            //获取冲突结果
            LinkedHashMap<List<GaSchedule>, Integer> cost_map = schedule_cost(this.population, this.elite);
            for (List<GaSchedule> key : cost_map.keySet()) {
                //若发现冲突结果为0 则说明可将其当做最优排课结果返回
                bestScore = cost_map.get(key);
                /*System.out.println("冲突结果bestScore");
                System.out.println(bestScore);*/
                if (bestScore == 0) {
                    bestGaSchedule = key;
                    return bestGaSchedule;
                }
            }
            //精英种群集合
            for (List<GaSchedule> key : cost_map.keySet()) {
                newPopulation.add(key);
            }
            while (newPopulation.size() < this.popsize) {
                List<GaSchedule> tmp = new ArrayList<>();
                if (Math.random() < this.mutprob) {
                    //落在变异概率内 变异
                    tmp = mutate(newPopulation);
                } else {
                    //交叉
                    tmp = crossover(newPopulation);
                }
                newPopulation.add(tmp);
            }
            this.population = newPopulation;
        }
        throw new ServiceException("课程安排存在冲突，无法排课");
//        return bestGaSchedule;
    }

    /**
     * 变异 根据精英种群集合 在将其中随机一个染色体变异后 返回变异后的染色体
     *
     * @param eiltePopulation
     * @return
     */
    List<GaSchedule> mutate(List<List<GaSchedule>> eiltePopulation) {
        Random random = new Random();
        //选择变异哪一个精英种群中的染色体
        int getIndex = random.nextInt(eiltePopulation.size());
        List<GaSchedule> ep = eiltePopulation.get(getIndex);
        for (GaSchedule s : ep) {
            int pos = random.nextInt(2);
            if (pos == 0) {
                s.setClassroom(s.randClassroom());
            } /*else if (pos == 1) {
                s.setWeekday(s.randWeekDay());
            }*/ else if (pos == 2) {
                s.setSlot(s.randSlot());
            }
        }
        return ep;
    }

    /**
     * 交叉 根据精英种群集合 在将其中两个染色体交叉后 返回一个新的染色体
     *
     * @param eiltePopulation
     * @return
     */
    List<GaSchedule> crossover(List<List<GaSchedule>> eiltePopulation) {
        Random random = new Random();
        //选择变异哪两个精英种群
        int getIndex1 = random.nextInt(eiltePopulation.size());
        int getIndex2 = random.nextInt(eiltePopulation.size());

        List<GaSchedule> e1 = eiltePopulation.get(getIndex1);
        List<GaSchedule> e2 = eiltePopulation.get(getIndex2);
        int pos = random.nextInt(2);
        for (int i = 0; i < e1.size(); i++) {
            if (pos == 0) {
                e1.get(i).setClassroom(e2.get(i).getClassroom());
            } /*else if (pos == 1) {
                e1.get(i).setWeekday(e2.get(i).getWeekday());
            }*/ else if (pos == 2) {
                e1.get(i).setSlot(e2.get(i).getSlot());
            }
        }
        return e1;
    }


    /**
     * 随机初始化不同的种群
     *
     * @param gaSchedules
     */
    void init_population(List<GaSchedule> gaSchedules) {
        this.population = new ArrayList<>();
        for (int i = 0; i < this.popsize; i++) {
            List<GaSchedule> entity = new ArrayList<>(this.initPopulation);
            for (int j = 0; j < gaSchedules.size(); j++) {
                GaSchedule tmp = gaSchedules.get(j);
                tmp.random_init();
                /*entity.add(new GaSchedule(
                        tmp.getTeachtaskid(),
                        tmp.getType(),
                        tmp.getClassroom(),
                        tmp.getWeekday(),
                        tmp.getSlot()
                ));*/
                entity.add(new GaSchedule(
                        tmp.getTeachtaskid(),
                        tmp.getSlots(),
//                        tmp.getType(),
                        tmp.getClassrooms(),
                        tmp.getClassroom(),
//                        tmp.getWeekday(),
                        tmp.getSlot(),
                        tmp.isCanEdit()
                ));
            }
            this.population.add(entity);
        }
    }


    /**
     * 计算课表种群的冲突。
     * 返回：精英种群--精英种群中排名第一的染色体若冲突值为0则说明是可以作为最优解返回
     * 当被测试课表冲突为0的时候，这个课表就是个符合规定的课表。
     * 冲突检测遵循下面几条规则：
     * 同一个教室在同一个时间只能有一门课。
     * 同一个班级在同一个时间只能有一门课。
     * 同一个教师在同一个时间只能有一门课。
     * 但是对于目前系统中已经将班级、教师、课程拼成了一条教学任务
     * 故只需要满足 同一个教室在同一个时间 只能有一各教学任务
     *
     * @param population
     * @param elite
     * @return
     */
    LinkedHashMap<List<GaSchedule>, Integer> schedule_cost(List<List<GaSchedule>> population, Integer elite) {
        LinkedHashMap<List<GaSchedule>, Integer> utilMap = new LinkedHashMap();
        LinkedHashMap<List<GaSchedule>, Integer> resMap = new LinkedHashMap();
        List<Integer> conflicts = new ArrayList<>();
        //一个染色体有多长==》有多少课程需要安排
        int n = population.get(0).size();

        for (List<GaSchedule> p : population) {
            //对种群遍历，求种群中染色体的适应度
            int conflict = 0;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    //check course in same time and same room
                    //检查冲突 需保证 在同一天 同一节课 下的 同一个教室中没有两个课程
                    if (p.get(i).getClassroom().getId().equals(p.get(j).getClassroom().getId())/* &&
                            p.get(i).getWeekday() == p.get(j).getWeekday()*/ &&
                            p.get(i).getSlot() == p.get(j).getSlot()) {
                        conflict += 1;
                    }
                }
            }
            utilMap.put(p, conflict);
        }
        //根据冲突值排序
        List<Map.Entry<List<GaSchedule>, Integer>> entryList = new ArrayList<Map.Entry<List<GaSchedule>, Integer>>(utilMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<List<GaSchedule>, Integer>>() {
            @Override
            public int compare(Map.Entry<List<GaSchedule>, Integer> me1, Map.Entry<List<GaSchedule>, Integer> me2) {
                //按照从小到大的顺序排列
                return me1.getValue().compareTo(me2.getValue());
            }
        });
        Iterator<Map.Entry<List<GaSchedule>, Integer>> iter = entryList.iterator();
        Map.Entry<List<GaSchedule>, Integer> tmpEntry = null;
        int flag = 0;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            resMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            flag++;
            if (flag == elite)
                break;
        }
        //说明一下：此处的resMap的大小会变化 因为排序后可能会出现相同的种群情况 因为把value值更新了
        return resMap;
    }


}
