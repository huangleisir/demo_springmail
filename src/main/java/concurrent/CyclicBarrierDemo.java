package concurrent;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
public class CyclicBarrierDemo {
	public static void main(String[] args) throws Exception {
		final CyclicBarrier cb = new CyclicBarrier(100);
		final CyclicBarrier cb2 = new CyclicBarrier(100);
		for(int i=1;i<101;i++){
			new Thread(new Runnable() {
				public void run() {
					try {
					Thread.sleep(new Random().nextInt(200));
					System.out.println("该运动员即将准备就绪:"+Thread.currentThread().getName());
					cb.await();//cb1 计数器加1,加到100的时候自动开始执行后面的语句
					System.out.println("该运动员从起跑线出发了"+Thread.currentThread().getName());
					Thread.sleep(new Random().nextInt(100));
					System.out.println("该运动员已经到达终点线，等待所有人到达后报告成绩:"+Thread.currentThread().getName());
					cb2.await();//cb12计数器加1,加到100的时候自动开始执行后面的语句
					System.out.println("该运动员报告自己的成绩"+Thread.currentThread().getName());
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
