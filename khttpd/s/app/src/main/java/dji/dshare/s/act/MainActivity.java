package dji.dshare.s.act;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.io.IOException;

import act.dshare.dji.R;
import k.httpd.s.HelloServer;
import k.httpd.s.KFileScanner;


public class MainActivity extends Activity {

	private SimpleServer server;
	private HelloServer server2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//testV1();
		//DshareServer2.main(new String[]{"",""});
	//	testV2();
		init();

	}

	private void init() {
		findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				KFileScanner.test();
			}
		});
	}

	private void testV1() {
		server = new SimpleServer();
		try {

			// 因为程序模拟的是html放置在asset目录下，
			// 所以在这里存储一下AssetManager的指针。
			server.asset_mgr = this.getAssets();

			// 启动web服务
			server.start();

			Log.i("Httpd", "The server started.");
		} catch(IOException ioe) {
		    Log.w("Httpd", "The server could not start.");
		}
	}
	private void testV2() {
		server2 = new HelloServer();
		try {
			// 启动web服务
			server2.start();
			Log.i("Httpd", "The server started.");
		} catch(IOException ioe) {
			Log.w("Httpd", "The server could not start.");
		}
	}
	private void testV3() {
		/*ServerConfig serverConfig = new ServerConfig();
		serverConfig.getRouter().addMapper("", DemoController.class);
		new WebServerBuilder.Builder().serverConfig(serverConfig).build().startWithThread();*/
	}

	@Override
    public void onDestroy()
    {
        super.onDestroy();
        
        if (server != null){
        	// 在程序退出时关闭web服务器
            server.stop();
    	}

    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
