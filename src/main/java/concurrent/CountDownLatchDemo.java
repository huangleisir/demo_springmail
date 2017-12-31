package concurrent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
public class CountDownLatchDemo {
	public static void main(String[] args) throws Exception {
		List<String> arrayList = new ArrayList<String>();
		final CountDownLatch cd1 = new CountDownLatch(100);
		final CountDownLatch cd2 = new CountDownLatch(1);
		final CountDownLatch runOver = new CountDownLatch(100);
		final CountDownLatch report = new CountDownLatch(1);
		for(int i=1;i<101;i++){
			new Thread(new Runnable() {
				public void run() {
					try {
					Thread.sleep(new Random().nextInt(200));
					System.out.println("该运动员已到达起跑线,准备好了:"+Thread.currentThread().getName());
					cd1.countDown();
					cd2.await();
					System.out.println("从起跑线出发了"+Thread.currentThread().getName());
					Thread.sleep(new Random().nextInt(100));
					System.out.println("该运动员已到达终点,等待裁判通知报告成绩:"+Thread.currentThread().getName());
					runOver.countDown();
					report.await();
					System.out.println("报告自己成绩"+Thread.currentThread().getName());
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		cd1.await();
		System.out.println("所有运动员已经准备好了，裁判扣动扳机");
		cd2.countDown();
		runOver.await();
		System.out.println("~~~~~裁判员等待所有运动员~都已经跑完了~~~~~~~");
		report.countDown();
		
	}
}
