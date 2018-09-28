package activity.huafeng.com.myapplication1.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import activity.huafeng.com.myapplication1.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 用于升级app的 服务
 */

public class UpdateService extends Service {

	private static final String TAG = "UpdateService";
	// 文件存储
	private File updateDir = null;
	private File updateFile = null;
	// 下载状态
	private final static int DOWNLOAD_COMPLETE = 555;
	private final static int DOWNLOAD_FAIL = 556;
	// 通知栏
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	// 通知栏跳转Intent
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;
	/***
	 * 创建通知栏
	 */
	RemoteViews contentView;
	// 这样的下载代码很多，我就不做过多的说明
	int downloadCount = 0;
	int currentSize = 0;
	long totalSize = 0;
	int updateTotalSize = 0;
	private String downloadPath;

	/*
       * onStartCommand()方法就是刚启动service时调用的一个方法
       * 在onStartCommand()方法中准备相关的下载工作：

    */

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 获取下载路径
		downloadPath = intent.getStringExtra("appurl");
		Log .d("step1", String.valueOf( downloadPath) );

		// 创建文件
		if (Environment.MEDIA_MOUNTED.equals( Environment.getExternalStorageState())) {
			updateDir = new File( Environment.getExternalStorageDirectory(),	Config.savePath);
			updateFile = new File(updateDir + Config.saveFileName);

			Log .d("step1", String.valueOf( updateDir ) );
			Log .d("step1", String.valueOf( updateFile ) );
		}

		this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification.Builder builder = new Notification.Builder(UpdateService.this);

		// 设置通知栏显示内容
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setContentTitle("开始下载");
		updateNotification = builder.build();

		// 发出通知
		updateNotificationManager.notify(0, updateNotification);

		updateIntent  = new Intent(this,UpdateService.class);

		// 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
		new Thread(new updateRunnable()).start();// 这个是下载的重点，是下载的过程

		Log .d("Thread","updateRunnable");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@SuppressLint("HandlerLeak")
	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case DOWNLOAD_COMPLETE:
					// 点击安装PendingIntent
					Uri uri = Uri.fromFile(updateFile);
					Intent installIntent = new Intent( Intent.ACTION_VIEW);
					installIntent.setDataAndType(uri,"application/vnd.android.package-archive");
//
					updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, installIntent, 0);

					Notification.Builder builder = new Notification.Builder(UpdateService.this);
					builder.setContentTitle("铅封系统更新");
					builder.setContentText("下载完成,点击安装");
					builder.setSmallIcon(R.mipmap.ic_launcher);
					builder.setContentIntent(updatePendingIntent);// 点设置击事件
					builder.setAutoCancel(true);

					updateNotification = builder.build();
	//				updateNotification.setLatestEventInfo(UpdateService.this,
	//						"QQ", "下载完成,点击安装。", updatePendingIntent);
					updateNotificationManager.notify(0, updateNotification);

					// 停止服务
					stopService(updateIntent);
				case DOWNLOAD_FAIL:
//					Notification.Builder builder1 = new Notification.Builder(UpdateService.this);
//					builder1.setContentTitle("铅封系统更新");
//					builder1.setContentText("下载失败");
//					builder1.setSmallIcon(R.mipmap.ic_launcher);
//					builder1.setAutoCancel(false);
//
//					updateNotification = builder1.build();
//					updateNotificationManager.notify(0, updateNotification);
					stopService(updateIntent);

				default:
					stopService(updateIntent);
			}
		}
	};


	/**
	 * 下载线程
	 */
	class updateRunnable implements Runnable {
		public void run() {
			Message message = updateHandler.obtainMessage();
			message.what = DOWNLOAD_COMPLETE;
			Log .d("Thread","下载进度");
			try {
				// 增加权限<USES-PERMISSION
				// android:name="android.permission.WRITE_EXTERNAL_STORAGE">;

				if (!updateDir.exists()) {
					updateDir.mkdirs();
				}
				if (!updateFile.exists()) {
					updateFile.createNewFile();
				}else{
					updateFile.delete();
					updateFile.createNewFile();
				}
				// 下载函数，以QQ为例子
				// 增加权限<USES-PERMISSION
				// android:name="android.permission.INTERNET">;
				long downloadSize =	downloadUpdateFile(downloadPath,updateFile);
				if (downloadSize > 0) {
					// 下载成功
					updateHandler.sendMessage(message);
				} 
			} catch (Exception ex) {
				ex.printStackTrace();
//				message.what = DOWNLOAD_FAIL;
				// 下载失败
//				updateHandler.sendMessage(message);
			}
		}
	}

	/**
	 * 下载文件的方法
	 */
	public long downloadUpdateFile(String downloadUrl, File saveFile)
			throws Exception {

		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;
		Log .d("Thread","downloadUpdateFile");
		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes="
						+ currentSize + "-");
			}
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);
			byte buffer[] = new byte[4096];
			int readsize = 0;

			Log .d("Thread", String.valueOf( is.read(buffer) ) );
			
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
				if ((downloadCount == 0) || (int) (totalSize * 100 / updateTotalSize) - 10 > downloadCount) {
					downloadCount += 10;

				Log.d("Thread","Notification");
					Notification.Builder builder = new Notification.Builder(this);
					builder.setContentTitle("铅封系统更新");
					builder.setContentText("已下载" + (int) totalSize * 100 / updateTotalSize + "%");
					builder.setSmallIcon(R.mipmap.ic_launcher);
					builder.setAutoCancel(false);

					updateNotification = builder.build();

					updateNotificationManager.notify(0, updateNotification);
				}
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return totalSize;
	}

}
