package ATM;

import java.security.DrbgParameters.NextBytes;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.management.monitor.MonitorSettingException;

import org.junit.platform.reporting.shadow.org.opentest4j.reporting.events.core.UserName;
import org.w3c.dom.events.MouseEvent;

//实现主页面
public class ATMsystem {
    public static void main(String[] args) {
        ArrayList<Account> arrayList = new ArrayList<>();
        // 集合:
        // 扫描器：
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("==============ATM==============");
            System.out.println("1.账户登录");
            System.out.println("2.账户开户");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    // 登录;
                    login(arrayList, sc);
                    break;
                case 2:
                    // 开户
                    register(arrayList, sc);
                    break;
                default:
                    System.out.println("没有这个命令，请重试");
            }
        }
    }

    /**
     * 登录功能的实现
     * 
     * @param arrayList
     * @param sc
     */
    private static void login(ArrayList<Account> arrayList, Scanner sc) {
        if (arrayList.size() == 0) {
            System.out.println("当前没有任何账户");
            return;
        }
        while (true) {
            System.out.println("请输入用户卡号");
            String id = sc.next();
            Account acc = getAccountBycradID(id, arrayList);
            if (acc == null) {
                System.out.println("系统中不存在该账户卡号");
            } else {
                while (true) {
                    System.out.println("请输入密码");
                    String passWord = sc.next();
                    if (acc.passWord.equals(passWord)) {
                        System.out.println("恭喜您！" + acc.username + ",登录成功！");
                        // 登录成功后页面：
                        showUsercommond(acc, sc, arrayList);
                        return;// 登录方法结束;
                    } else {
                        System.out.println("您输入的密码有误");
                    }
                }

            }
        }

    }

    /**
     * 展示登录后的操作页面;
     */
    private static void showUsercommond(Account acc, Scanner sc, ArrayList<Account> arrayList) {
        while (true) {
            System.out.println("=============用户操作页=============");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.修改密码");
            System.out.println("6.退出系统");
            System.out.println("7.注销账户");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    showAccount(acc);
                    break;
                case 2:// 存款:拿到对象+存款+修改;
                    changeMoney(acc, sc);
                    break;
                case 3:// 取款：拿到对象+取款+修改
                    Withdrawmoney(acc, sc);
                    break;
                case 4:// 要求两个账户以上可以转账，判断账户是否有钱
                    transfermoney(acc, sc, arrayList);
                    break;
                case 5:
                    updataPassword(acc, sc);
                    break;
                case 6:// 退出
                    System.out.println("退出成功！");
                    return;// 关闭当前方法;
                case 7:// 销户
                    deleteAccount(acc, arrayList, sc);
                    break;
                default:
                    System.out.println("你输入的命令不正确");
                    break;
            }
        }

    }

    private static void deleteAccount(Account acc, ArrayList<Account> arrayList, Scanner sc) {
        System.out.println("请问您真的要销户吗");
        String rs = sc.next();
        while (true) {
            switch (rs) {
                case "Y":
                    arrayList.remove(acc);
                    System.out.println("您当前的账户已经删除");
                    return;
                case "N":
                    System.out.println("您当前的账户依然保留");
                    return;
                default:
                    System.out.println("请输入Y/N");
            }
        }
    }

    /**
     * 修改密码
     * 
     * @param acc 当前账户对象
     * @param sc  扫描器
     */
    private static void updataPassword(Account acc, Scanner sc) {
        System.out.println("============用户密码修改============");
        while (true) {
            System.out.println("请输入当前密码：");
            String passWord = sc.next();
            if (passWord.equals(acc.getPassWord())) {
                System.out.println("请输入你想要修改的密码");
                String tmp = sc.next();
                acc.setPassWord(tmp);
                System.out.println("您修改密码成功！");
                return;
            } else {
                System.out.println("您当前密码不正确!");
            }

        }
    }

    /**
     * 转账功能
     * 
     * @param acc
     * @param sc
     * @param arrayList
     */
    private static void transfermoney(Account acc, Scanner sc, ArrayList<Account> arrayList) {
        System.out.println("============用户转账============");
        if (arrayList.size() < 2) {
            System.out.println("账户对象不足两个，请先开户");
            return;
        }
        while (true) {
            System.out.println("请输入转账金额：");
            double money = sc.nextDouble();
            if (money > acc.getMoney()) {
                System.out.println("当前账户余额不足，请重新输入");
                continue;
            }
            System.out.println("请输入转账对象的卡号：");
            while (true) {
                String id = sc.next();
                if (id.equals(acc.cardID)) {
                    System.out.println("你无法给自己转账");
                    continue;
                }
                Account target = getAccountBycradID(id, arrayList);
                if (target == null) {
                    System.out.println("目标账户不存在，请重新输入");
                } else {
                    target.setMoney(target.getMoney() + money);
                    acc.setMoney(acc.getMoney() - money);
                    System.out.println("恭喜您，转账成功,当前账户余额如下：" + acc.getMoney());
                    return;// 直到转完账;
                }
            }
        }

    }

    private static void Withdrawmoney(Account acc, Scanner sc) {
        System.out.println("============用户取款============");
        if (acc.getMoney() == 0) {
            System.out.println("您的账户余额为0，不能取款");
            return;
        }
        System.out.println("当前你的账户余额为" + acc.getMoney() + "请输入取款金额");
        double money = sc.nextDouble();
        while (true) {
            if (money > acc.getMoney()) {
                System.out.println("您账户余额不足！");
            } else {
                if (money > acc.getQuotoMoney()) {
                    System.out.println("对不起，您当前取款金额超过每次取款限额，您设置的限额为：" + acc.getQuotoMoney());
                } else {
                    acc.setMoney(acc.getMoney() - money);
                    System.out.println("恭喜您，取款成功,当前账户余额如下：" + acc.getMoney());
                    return;
                }

            }
        }
    }

    private static void changeMoney(Account acc, Scanner sc) {
        System.out.println("===========用户存款===========");
        System.out.println("请输入存款金额");
        double money = sc.nextDouble();
        acc.setMoney(money + acc.getMoney());
        System.out.println("恭喜您，存款成功,当前账户余额如下：" + acc.getMoney());
    }

    /**
     * 展示账户
     * 
     * @param acc
     */
    private static void showAccount(Account acc) {
        System.out.println("===================当前账户信息如下===================");
        System.out.println("卡号：" + acc.getCardID());
        System.out.println("户主" + acc.username);
        System.out.println("余额" + acc.getMoney());
    }

    /**
     * 用户开户功能的实现
     * 
     * @param arrayList 账户集合
     */
    private static void register(ArrayList<Account> arrayList, Scanner sc) {
        System.out.println("==================系统开户操作==================");
        // 开户
        Account account = new Account();
        // 录入账号和密码
        System.out.println("请输入你的用户名：");
        String username = sc.next();
        account.setUsername(username);
        while (true) {
            System.out.println("请输入你的密码：");
            String password = sc.next();
            System.out.println("请确认你的密码：");
            String okpassword = sc.next();
            if (okpassword.equals(password)) {
                account.setPassWord(okpassword);
                break;
            } else {
                System.out.println("密码不一致，请重试");
            }
        }
        System.out.println("请输入每次取款限额");
        double quotoMoney = sc.nextDouble();
        account.setQuotoMoney(quotoMoney);
        String cardId = getRandomCardID(arrayList);
        account.setCardID(cardId);
        // 添加到集合中去
        arrayList.add(account);
        System.out.println("恭喜您，" + username + ",您开户成功，您的卡号是：" + cardId + ",请您妥善保管!");

    }

    /**
     * 为账户生成8位卡号
     * 
     * @return
     */
    private static String getRandomCardID(ArrayList<Account> accounts) {
        Random r = new Random();
        while (true) {
            String id = "";
            id += r.nextInt(10000000, 99999999);
            // 查询：
            Account tmp = getAccountBycradID(id, accounts);
            if (tmp == null) {
                return id;
            }
        }

    }

    /**
     * 根据卡号查到账户
     * 
     * @param id
     * @param accounts
     * @return
     */
    private static Account getAccountBycradID(String id, ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account tmp = accounts.get(i);
            if (tmp.getCardID().equals(id)) {
                return tmp;
            }
        }
        return null;
    }
}
