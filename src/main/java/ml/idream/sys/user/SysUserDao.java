package ml.idream.sys.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapper;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
* UserDao
* */
@Mapper
public interface SysUserDao{

    @Select("select * from sys_user where name like %${name}% order by id limit 0,1")
    SysUser findByNameLikeOrderByIdAsc(String name);

    @Select("select * from sys_user where createdate < #{date}")
    List<SysUser> getByCreatedateLessThan(Date date);

    @Select("select * from sys_user where name = #{name}")
    SysUser findByNameEquals(String name);

    @Options(useGeneratedKeys = true)
    @Insert("insert into sys_user (name,password) values (#{name},#{password})")
    void save(SysUser user);

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = UserDaoProvider.class,method = "insertUserRole")
    void saveUserRole(List<Map<String,Object>> userRole);

    @Delete("delete from sys_user")
    void deleteAll();
}

class UserDaoProvider{
    public String insertUserRole(Map<String,Object> param){

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO SYS_USER_ROLE ");

        List<Map<String,Object>> list = (List<Map<String, Object>>) param.get("list");
        boolean isColume = false;
        if(list.size() <= 0){
            return "";
        }
        sb.append("(");
        Map<String,Object> first = list.get(0);
        String formatStr = "(";
        for(Map.Entry entry : first.entrySet()){
            sb.append(entry.getKey()).append(",");
            formatStr += "#{list[{0}]." + entry.getKey() + "},";
        }
        formatStr = StringUtils.substringBeforeLast(formatStr,",");
        formatStr += ") ";
        sb.append(") VALUES ");
        MessageFormat mf = new MessageFormat(formatStr);
        for(int i = 0; i < list.size(); ++i){
            sb.append(mf.format(new Object[]{i})).append(",");
        }
        return sb.substring(0,sb.capacity());
    }
}