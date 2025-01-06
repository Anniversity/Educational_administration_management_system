package cn.zjw.edubackend.common;

import javax.validation.groups.Default;

public class ValidateGroup {
    public interface query extends Default {}
    public interface insert extends Default {}
    public interface update extends Default {}
}
