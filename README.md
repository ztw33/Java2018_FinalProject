# JAVA2018_Final Project

## 运行效果展示
![](game.gif)
## 概述
### 完成情况
完成了所有要求的基本功能，并实现了战斗记录与回放、增加了注解、为部分方法编写了单元测试用例、使用了maven进行构建管理，并根据动画中人物属性设定进行还原，使得战斗画面尽量生动。
### 游戏说明
游戏有开始界面和战斗主界面，点击“开始游戏”或“游戏回放”可进入战斗主界面。在主界面可通过点击屏幕左侧按钮进行相应操作，也可通过键盘进行游戏控制。游戏开始后，每个生物在战场上随机行走，并释放自己的技能。当葫芦娃阵营生物和妖精阵营生物正面相遇时发生交战事件，必有一个生物死亡。当有一方全部死亡时游戏结束，玩家可选择存档与否。   
#### 人物设定
| 人物        | 属性           |
| ------------- |:-------------:|
| 大娃 | 生命值高，攻击力强 |
| 二娃 | 行走速度快      |
| 三娃 | 生命值高，防御力强|
|四娃|可朝前方两个格子的范围喷射火焰攻击敌人|
|五娃|可朝上下左右4个格子喷水攻击敌人|
|六娃|防御较高|
|七娃|可向前发射宝葫芦，伤害较高|
|爷爷|每走7步可对所有运动中的葫芦娃进行治疗回血|
|小喽啰|属性均衡|
|蝎子精|可向前扔出砍刀，伤害较高|
|蛇精|每走10步可以给所有的妖精(包括自己)进行治疗回血|

## 实现说明
### 类的定义
因为游戏中出现了一些非生物（火、水、砍刀、宝葫芦），所以新定义了抽象类Thing，skillThing(水火那些)和Creature都是抽象类，它们继承了Thing类。爷爷和蛇精具有治疗的能力，四娃、五娃、七娃和蝎子精具有释放技能的能力，所以它们分别实现了Cure和Shoot接口。（说明：由于每个葫芦娃的能力不一样，所以单独定义了从大娃到七娃的类继承自葫芦娃类）阵型类的定义沿袭了project3.

### 关于多线程
在游戏中，除了每个生物实现为一个线程之外，每个技能物也是runnable的、GUI绘制需要实现为一个线程(不然页面会没有响应)。当两个生物相遇发生战斗事件，这个事件也是一个线程。每个线程有自己的run方法，在run的过程中需要注意同步问题。由于生物线程、技能物线程、GUI绘制线程在run的过程中都是在对战场信息进行读取和更新，所以防止访问冲突的关键就是对战场信息上锁，使得每次只有一个线程能够访问和修改战场信息。具体实现如下：   
所有的物体都需要能够读取战场信息，根据战场信息来决定自己的行为。所以Thing类持有BattleField类的成员变量，它是static的：
```java
public abstract class Thing {
	...
	protected static BattleField field;
	public static void setField(BattleField field) {
		Thing.field = field;
	}
	...
}
```
每个生物在每一次移动时需要对战场信息上锁，避免走到同一个位置里去。以葫芦娃的run方法为例说明如下：
```java
while(!isKilled) {
	synchronized (field) {
		if(state == CreatureState.RUNNING) {
			// 前方有妖精，触发战斗事件
			if (field.existBadCreature(position.getRow(), position.getColumn()+1)) {
            ...
		    } else { // 在下一个位置放置
		        setCreatureOnNextPosition(getNextPosition());
		    }
		}
		...
	}
}
```
对于一个技能物来说，在它移动以及判断是否对目标敌人造成伤害时也需要对战场信息上锁，不然可能出现实际上敌人已经移动但仍然判断其击中的情况。以Fire的run方法为例：
```java
synchronized (field) {
    /* 击中敌人 */
	if (field.existBadCreature(coordPosition.getRow(), coordPosition.getColumn())) {
		/* 对敌人造成伤害 */
		...
    }
}
```
技能物是由生物释放的，那么我该如何管理这些被释放的技能物呢？既然所有生物都持有战场battleField，那么每个生物释放技能，都将交给战场去管理，战场收集这些技能物并execute它们的run方法。这样方便绘制，也方便统一管理。例如当处于战斗过程中用户关闭了窗口，在监听关闭事件的处理函数中需要统一kill这些线程。    
`需要特别考虑线程关闭的问题。在战斗过程中当玩家点击“重新开始”或者直接关闭窗口时，正在run的线程应该被强制关闭。`
### 战斗记录与回放
GUI每100ms刷新一次显示当前战场信息。那么在战斗进行中进行GUI绘制时，保存每一帧的信息，回放时读取每一帧信息再按100ms的速率显示每一帧图像即可。绘制即绘制战场信息，一帧的战场信息需要包括以下信息即可完整还原当前状态：
- 当前未被kill的生物总数以及每个生物的位置、状态（若为CURE状态则需要绘制治疗时特效、若为DEAD状态则绘制墓碑、运动和战斗状态绘制人物形象）、血量百分比（用来绘制血量条）
- 所有技能物的总数以及每个技能物的种类（是水、火、刀还是葫芦）和位置   

所以最后我的记录文件的组织如下所示：
```
整个战斗过程的帧总数n   
{ // 每一帧信息    
    当前需要绘制的生物总数m   
    { // 每一个生物信息
        名字 血量百分比 位置
    } *m
    当前需要绘制的技能物总数k
    { // 每一个技能物信息
        种类 位置
    } *k
} *n
战斗结果(1表示葫芦娃胜出，0表示妖精胜出)
```
读取时按上述定义的格式从文件中读出即可。
### 课堂内容实践
#### 集合框架与泛型
阵法表示为一个“具有排列生物功能”的接口，定义了arrangeCreature方法。其中，方法接收了一个ArrayList的参数表示当前要排列的生物集合：
```java
public interface ArrangeCreaturesInterface {
	public boolean arrangeCreature(BattleField bf, ArrayList<? extends Creature> creatures, int startRow, int startColumn);
}
```
每一个具体的阵法类都实现了这个接口并实现arrangeCreature方法，以长蛇阵类为例：
```java
public class ChangShe<T extends Creature> implements ArrangeCreaturesInterface {
	@Override
	public boolean arrangeCreature(BattleField bf, ArrayList<? extends Creature> creatures, int startRow, int startColumn) {
		/* 具体实现 */
	}
}
```
ChangShe类是一个泛型类，它接收Creature及其子类，用来表示“这是排列某种特定生物的阵法”。ArrayList参数为<? extends Creature>表示是一个可以存放`某一种特定的`任意Creature子类的一组变量。通过泛型的方法我们可以定义一个"只排列葫芦娃阵营"的长蛇阵类和一个“只排列妖精阵营”的长蛇阵类，如下所示：
```java
ArrayList<Good> goods = new ArrayList<>();
... // goods里存放着葫芦娃和老爷爷
ChangShe<Good> formation = new ChangShe<Good>();
formation.arrangeCreature(battleField, goods, goodsStartRow, goodsStartColumn);

ArrayList<Bad> bads = new ArrayList<>();
... // bads里存放着小喽啰、蛇精和蝎子精
ChangShe<Bad> formation = new ChangShe<Bad>();
formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
```
#### 注解
自定义了一个注解类用来标记编程过程中需要特别注意的代码片段：
```java
/*
 * 编程中曾经犯过的错误、和值得注意的地方
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Notice {
	String message(); // 消息
	int lineBegin(); // 相关代码起始行数
	int lineEnd(); // 结束行数
}
```
如下所示的使用表示193-197行的注释表示一个不正确的删除ArrayList中元素的方法，需要注意：
```java
@Notice(message="不正确的删除ArrayList中元素的方法", lineBegin=193, lineEnd=197)
```
#### 单元测试
为表示双方正面交锋的战斗事件编写了几个单元测试用例，用来测试战斗结果和战斗时间。
#### 异常处理
主要在文件IO处使用。
#### 设计模式
在BattleField和Coord类的设计实现中用到了代理模式。战场拥有很多Coord，比如生物要走动到某一个Coord上，生物不是直接向Coord发送信息，而是向BattleField发送信息，然后BattleField再将生物放置到指定的Coord上。BattleField是Coord的代理。这样做的好处是在执行动作之前可以做额外操作，比如生物有可能要走到超出战场范围的坐标点上，BattleField在向Coord对象发送消息之前首先判断目标位置有没有越界，如果越界直接返回，没有越界才执行相应的放置操作。
#### 设计原则
##### 开放封闭原则
开放封闭原则是指实体应该对扩展是开放的，对修改是封闭的。即，可扩展，不可修改。应该通过增加代码来扩展功能，而不是修改已经存在的代码。在阵法类的设计上很好的贯彻了这条原则：所有具体阵法都implements“排列生物”的接口，如果需要增加一个新的阵法，只需要implements这个接口并实现接口方法即可。而不是通过参数判断当前要排列的是什么阵法，再去进行相关操作。
##### 依赖倒置原则
高层模块不应该依赖于低层模块，二者都应该依赖于抽象。所以，任何变量都不应该持有一个指向具体类的指针或引用。举例说明，一个Coord里存放了一个抽象类Creature表示当前持有这个坐标点的生物，而不能写为某个具体类。任何类都不应该从具体类派生，我觉得这一点是很符合现实世界的。如果一个类可以派生出其他类，证明这个类表示的是一种属性，是一种抽象。具体类可以被实例化，而一个实体不可能作为抽象物质存在，正如没有一个生物只作为“生物”类别而没有其他更细的类别存在于这个世界上，它既然是一个具体的生物，就一定是某种确切的生物。
## 结语
通过这学期的JAVA课学习，我感受最深的一点就是“面向对象是对现实世界的刻画”。在面向对象编程的过程中，我不断的探索着程序与现实世界的联系。很多上课时听的云里雾里、不知道为什么要这样设计的地方，在自己编程实现的过程中，可能会突然恍然大悟：现实世界里就是这样的，它表现的就是现实世界里的关系。