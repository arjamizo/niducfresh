package net.netii.niducproject;


public class ThreadEntryRefresher implements Runnable {
	
	private MainActivity activity;
	public ThreadEntryRefresher(MainActivity activity) {
		this.activity = activity;
	}
	@Override
	public void run() {
		//while(true)
		{
			activity.updateRows();
			/*try {
				Thread.sleep(333);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

}
