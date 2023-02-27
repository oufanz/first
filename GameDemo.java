package DAY2y27dCollecion_test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GameDemo {
    /**
     * 1.一个静态集合存牌
     */
    public static List<Card> allCards = new ArrayList<>();

    /*
     * 2.做牌：定义静态代码块初始化数据;
     */
    static {
        String[] sizes = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        //定义花色：
        String[] colors = {"方块", "红桃", "黑桃", "梅花"};
        int index = 0;
        for (String a : sizes) {
            index++;
            for (String b : colors) {
                Card c = new Card(a, b, index);
                allCards.add(c);
            }
        }
        Card c1 = new Card("", "大王", ++index);
        Card c2 = new Card("", "小王", ++index);
        Collections.addAll(allCards, c1, c2);
        System.out.println("新牌" + allCards);
    }

    //上面都是初始搭建;
    public static void main(String[] args) {
        //洗牌：
        Collections.shuffle(allCards);
        System.out.println("洗牌后结果:" + allCards);
        //发牌：
        //每个玩家都是一个容器:用TreeSet排序:或者用list再排序
        List<Card> mem1 = new ArrayList<>();
        List<Card> mem2 = new ArrayList<>();
        List<Card> mem3 = new ArrayList<>();
        for (int i = 0; i < allCards.size(); i++) {
            Card c = allCards.get(i);
            if (i % 3 == 0) {
                mem1.add(c);
            } else if (i % 3 == 1) {
                mem2.add(c);
            } else {
                mem3.add(c);
            }
        }
        //拿到最后三张底牌:sublist截取成一个集合;
        List<Card> last = allCards.subList(allCards.size() - 3, allCards.size());
        //给玩家的牌进行排序;
        sortCards(mem1);
        sortCards(mem2);
        sortCards(mem3);
        //使用方法进行操作;
        //输出玩家的牌
        System.out.println("玩家1：" + mem1);
        System.out.println("玩家2：" + mem2);
        System.out.println("玩家3：" + mem3);
        System.out.println("三张底牌：" + last);
    }

    //需要一个索引进行考虑;再Card中多一个属性;
    private static void sortCards(List<Card> mem1) {
        mem1.sort((o1, o2) -> o1.getIndex()- o2.getIndex());
    }

}
