package concurrent;
public class JoinDemo implements Runnable {
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("join "+i);
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new JoinDemo());
		t.start();
		for (int i = 0; i < 100; i++) {
			if(i==50){
				t.join();
			}
			System.out.println("main "+i);
		}
	}
}
