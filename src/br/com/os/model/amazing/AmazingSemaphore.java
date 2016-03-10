package br.com.os.model.amazing;

import java.util.concurrent.Semaphore;

/** Just for not having to handle exceptions. */
public class AmazingSemaphore extends Semaphore {

	private static final long serialVersionUID = 6679850619550916493L;
	
	public AmazingSemaphore(int permits) {
		super(permits, true);
	}
	
	public void down() {
		try {
			this.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void up() {
		this.release();
	}
	
	public void up(int permits) {
		this.release(permits);
	}

}
