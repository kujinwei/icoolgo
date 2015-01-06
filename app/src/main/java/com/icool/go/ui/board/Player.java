package com.icool.go.ui.board;

import android.os.Handler;
import android.os.Message;

import com.icool.go.ui.BoardView;

public class Player implements Runnable {

	public final int UPDATE_BOARD = 1;
	BoardView board = null;
	Thread thr = null;

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_BOARD:
				board.goNext();
				board.invalidate();

				break;
			}
			super.handleMessage(msg);
		}
	};

	public Player(BoardView board) {
		this.board = board;
	}

	public void run() {

		try {
			while (thr == Thread.currentThread()
					&& !Thread.currentThread().isInterrupted()) {

				Message message = new Message();
				message.what = UPDATE_BOARD;
				myHandler.sendMessage(message);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Thread.currentThread().interrupt();
		}
	}

	public void start() {
		if (thr == null) {
			thr = new Thread(this);
		}

		thr.start();
	}

	public void stop() {
		if (thr != null) {
			thr = null;
		}

	}

}
