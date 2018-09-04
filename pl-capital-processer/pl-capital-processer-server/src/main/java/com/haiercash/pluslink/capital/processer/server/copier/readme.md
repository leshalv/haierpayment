**
VO->DO,DO->BO等，对象转化
例子：
**
```
public class LoanCopier {


    public static AssetsSplit voToDo(LoanRequest entity) {
        //如果对象属性一致，可以直接使用
        return BeanTemplate.executeBean(entity, AssetsSplit.class);
        
        //如果不一致---------------
        vo = BeanTemplate.executeBean(entity, AssetsSplit.class);
        //不一致属性处理
        vo.set属性(entity.get属性)
        return vo;
    }
}
```
