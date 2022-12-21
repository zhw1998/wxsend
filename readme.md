

## ** 微信公众号消息推送**

## 使用流程
    新建:
        1. 用户需获取申请码 （方式待定）
        2. 注册微信公众号测试账号
        3. 进入网页端添加基本信息
        4. 选择消息模板，填写模板基本信息
        5. 新增接收人
        6. 复制选择的模板中内容，在微信公众号上创建模板
        7. 复制微信公众号上创建的模板id，填写到模板表单中
        8. 设置发送策略
        9. 提交表单
    
    修改:
        1. 输入申请码，点击查询
        2. 修改表单: 参考新建
        3. 提交表单

##模板策略
​    系统模板新建方式待定
​    
​    选择模板 -> 显示模板样式 -> 生成模板所需字段表单 -> 填写模板id -> 填写字段 -> 选择字体颜色



##定时策略
​    1. 选择执行策略 （待拓展）

        每天
        工作日
        休息日
        每星期（选择1-7）
        每月（选择1-31）
    
    2. 选择具体执行时间
        hh:mm



##模板字段拓展  
​    FiledMethod.class 

两个关键注解	

```java
//作用到方法上：返回到前端的数据,  name => 编辑模板时可见得字段名  value => 存入数据库和获取数据时的方法名
@Filed(name = "距离天数[yyyy-MM-dd]", value = "arrivedDays")
//作用到实体类属性上： 如果上面方法返回的是对象，需要在对象属性上添加该注解。用于返回 name 
@FiledName(name = "城市")
```



示例： 

1. 只返回字符串时

   ```java
   /**
    * 距离天数 yyyy-MM-dd
    * @param value
    * @return
    */
   @Filed(name = "距离天数[yyyy-MM-dd]", value = "arrivedDays")
   public String getArrivedDays(String value){
       Date birthday = DateUtil.convertString2Date(value);
       return DateUtil.diffDay(birthday, new Date())+"";
   }
   ```

2. 返回一个对象中多个属性时

   ```java
   /**
    * 获取天气
    * @param value 省份|地区
    * @return
    */
   @Filed(name = "天气[省份|地区]", value = "weather")
   public Weather getWeather(String value){
       String[] strs = value.split("\\|");
       String province = strs[0];
       String city = strs[1];
       String url = String.format(environment.getProperty("weather.url"), province, city);
       String weatherDataJson = HttpUtil.get(url);
       Weather weather = JSON.parseObject(weatherDataJson, Weather.class);
       return weather;
   }
   ```

   ```java
   @Data
   public class Weather extends FiledData {
   
       /**
        * {"city":"101210101","cityname":"杭州","fctime":"202212190800","temp":"8℃","tempn":"0℃","Weather":"晴","weathercode":"d0","weathercoden":"n0","wd":"无持续风向","ws":"<3级"}
        */
   
   
       /**
        * 城市
        */
       @FiledName(name = "城市")
       private String cityname;
   
       /**
        * 更新时间
        */
       @FiledName(name = "更新时间")
       private String fctime;
   
       /**
        * 最高温
        */
       @FiledName(name = "最高温")
       private String temp;
   
       /**
        * 最低温
        */
       @FiledName(name = "最低温")
       private String tempn;
   
       /**
        * 天气
        */
       @FiledName(name = "天气")
       private String weather;
   
       private String weathercode;
   
       private String weathercoden;
   
   
       /**
        * 风向
        */
       @FiledName(name = "风向")
       private String wd;
   
       /**
        * 风级
        */
       @FiledName(name = "风级")
       private String ws;
   
   
   
   }
   ```




##定时策略拓展

​	WayFilterUtil.class

​	这里返回值只能是 boolean 类型，

​	只有一个注解（和上面类似）：@Filed 

 1. 不需要填写策略值的:   newDate 必须

    ```java
    /**
     * 休息日
     *
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    @Filed(name = "休息日", value = "offday")
    private boolean offdayWay(Date nowDate){
        String date = DateUtil.convertDate2String(nowDate);
        DateDto dateDto = dateDtoMap.get(date);
        if(dateDto != null && dateDto.getIsWork() == DateDto.IsWork_no){
            return true;
        }
        return false;
    }
    ```

 2. 需要填写策略值得：nowDate 和 value 必须

    ```
    这里@Filed注解的name属性的使用：
    	策略名称 ：策略值 （目前只支持数据类型，前端可选值（value））
    ```



    ```java
    /**
     * 每月几
     *
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    @Filed(name = "每月:['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31']", value = "monthDay")
    private boolean isMonthDayWay(Date nowDate, String value){
        Integer day = Integer.parseInt(value);
        return day.equals(nowDate.getDate());
    }
    ```