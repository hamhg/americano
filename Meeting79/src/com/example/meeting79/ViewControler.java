package com.example.meeting79;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.adapter.ChatListAdapter;
import com.example.adapter.HeartListAdapter;
import com.example.meeting79.SlideView.SizeCallback;
import com.example.util.DeviceData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class ViewControler extends Activity {

	private LocationManager locationManager;
	private String provider;
	private LatLng myPos;
	private GoogleMap map;

	boolean click;

	SlideView slideView;
	View menu, app, heart, chat, profile, contact, setting, store, notice, event, qna, help;
	Button slide, slideHeart, slideChat, slideProfile, slideContact, slideSetting, slideStore, slideNotice, slideEvent, slideQna, slideHelp;
	WebView noticeView, eventView, qnaView, helpView; 

	View[] changeMenu = new View[2];
	SizeCallbackForMenu size;

	static boolean menuOut = false;

	LinearLayout menuFind, menuHeart, menuChat, menuProfile, menuContact,
			menuSetting, menuStore, menuNotice, menuEvent, menuQna, menuHelp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();

			StrictMode.setThreadPolicy(policy);
		}

		LayoutInflater inflater = LayoutInflater.from(this);
		slideView = (SlideView) inflater.inflate(R.layout.slide_view, null);
		setContentView(slideView);

		menu = inflater.inflate(R.layout.leftmenu, null);
		app = inflater.inflate(R.layout.activity_match, null);
		heart = inflater.inflate(R.layout.activity_heart, null);
		chat = inflater.inflate(R.layout.activity_chat, null);
		profile = inflater.inflate(R.layout.activity_profile, null);
		contact = inflater.inflate(R.layout.activity_contact, null);
		setting = inflater.inflate(R.layout.activity_setting, null);
		store = inflater.inflate(R.layout.activity_store, null);
		notice = inflater.inflate(R.layout.activity_notice, null);
		event = inflater.inflate(R.layout.activity_event, null);
		qna = inflater.inflate(R.layout.activity_qna, null);
		help = inflater.inflate(R.layout.activity_help, null);
		
		ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
		ViewGroup tabBarHeart = (ViewGroup) heart.findViewById(R.id.tabBarHeart);
		ViewGroup tabBarChat = (ViewGroup) chat.findViewById(R.id.tabBarChat);
		ViewGroup tabBarProfile = (ViewGroup) profile.findViewById(R.id.tabBarProfile);
		ViewGroup tabBarContact = (ViewGroup) contact.findViewById(R.id.tabBarContact);
		ViewGroup tabBarSetting = (ViewGroup) setting.findViewById(R.id.tabBarSetting);
		ViewGroup tabBarStore = (ViewGroup) store.findViewById(R.id.tabBarStore);
		ViewGroup tabBarNotice = (ViewGroup) notice.findViewById(R.id.tabBarNotice);
		ViewGroup tabBarEvent = (ViewGroup) event.findViewById(R.id.tabBarEvent);
		ViewGroup tabBarQna = (ViewGroup) qna.findViewById(R.id.tabBarQna);
		ViewGroup tabBarHelp = (ViewGroup) help.findViewById(R.id.tabBarHelp);

		slide = (Button) tabBar.findViewById(R.id.slide);
		slideHeart = (Button) tabBarHeart.findViewById(R.id.slideHeart);
		slideChat = (Button) tabBarChat.findViewById(R.id.slideChat);
		slideProfile = (Button) tabBarProfile.findViewById(R.id.slideProfile);
		slideContact = (Button) tabBarContact.findViewById(R.id.slideContact);
		slideSetting = (Button) tabBarSetting.findViewById(R.id.slideSetting);
		slideStore = (Button) tabBarStore.findViewById(R.id.slideStore);
		slideNotice = (Button) tabBarNotice.findViewById(R.id.slideNotice);
		slideEvent = (Button) tabBarEvent.findViewById(R.id.slideEvent);
		slideQna = (Button) tabBarQna.findViewById(R.id.slideQna);
		slideHelp = (Button) tabBarHelp.findViewById(R.id.slideHelp);

		slide.setOnClickListener(new MenuSlideListener(slideView, menu, app));
		slideHeart.setOnClickListener(new MenuSlideListener(slideView, menu, heart));
		slideChat.setOnClickListener(new MenuSlideListener(slideView, menu,	chat));
		slideProfile.setOnClickListener(new MenuSlideListener(slideView, menu, profile));
		slideContact.setOnClickListener(new MenuSlideListener(slideView, menu, contact));
		slideSetting.setOnClickListener(new MenuSlideListener(slideView, menu, setting));
		slideStore.setOnClickListener(new MenuSlideListener(slideView, menu, store));
		slideNotice.setOnClickListener(new MenuSlideListener(slideView, menu, notice));
		slideEvent.setOnClickListener(new MenuSlideListener(slideView, menu, event));
		slideQna.setOnClickListener(new MenuSlideListener(slideView, menu, qna));
		slideHelp.setOnClickListener(new MenuSlideListener(slideView, menu, help));
		
		noticeView = (WebView) notice.findViewById(R.id.web);
		eventView = (WebView) event.findViewById(R.id.web);
		qnaView = (WebView) qna.findViewById(R.id.web);
		helpView = (WebView) help.findViewById(R.id.web);
		
		noticeView.setWebViewClient(new WebViewClient());
		eventView.setWebViewClient(new WebViewClient());
		qnaView.setWebViewClient(new WebViewClient());
		helpView.setWebViewClient(new WebViewClient());
		
		WebSettings noticeSet = noticeView.getSettings();
		WebSettings eventSet = eventView.getSettings();
		WebSettings qnaSet = qnaView.getSettings();
		WebSettings helpSet = helpView.getSettings();
		
		noticeSet.setJavaScriptEnabled(true);
		eventSet.setJavaScriptEnabled(true);
		qnaSet.setJavaScriptEnabled(true);
		helpSet.setJavaScriptEnabled(true);

		// Leftmenu Event 등록
		menuFind = (LinearLayout) menu.findViewById(R.id.menuFind);
		menuHeart = (LinearLayout) menu.findViewById(R.id.menuHeart);
		menuChat = (LinearLayout) menu.findViewById(R.id.menuChat);
		menuProfile = (LinearLayout) menu.findViewById(R.id.menuProfile);
		menuContact = (LinearLayout) menu.findViewById(R.id.menuContact);
		menuSetting = (LinearLayout) menu.findViewById(R.id.menuSetting);
		menuStore = (LinearLayout) menu.findViewById(R.id.menuStore);
		menuNotice = (LinearLayout) menu.findViewById(R.id.menuNotice);
		menuEvent = (LinearLayout) menu.findViewById(R.id.menuEvent);
		menuQna = (LinearLayout) menu.findViewById(R.id.menuQna);
		menuHelp = (LinearLayout) menu.findViewById(R.id.menuHelp);

		menuFind.setOnClickListener(menuClickListener);
		menuHeart.setOnClickListener(menuClickListener);
		menuChat.setOnClickListener(menuClickListener);
		menuProfile.setOnClickListener(menuClickListener);
		menuContact.setOnClickListener(menuClickListener);
		menuSetting.setOnClickListener(menuClickListener);
		menuStore.setOnClickListener(menuClickListener);
		menuNotice.setOnClickListener(menuClickListener);
		menuEvent.setOnClickListener(menuClickListener);
		menuQna.setOnClickListener(menuClickListener);
		menuHelp.setOnClickListener(menuClickListener);

		int scrollToViewIdx = 1;
		setMapTransparent((ViewGroup) app); // GoogleMap Background Transparent
		initGmap();

		// 최초 로딩 화면 세팅
		changeMenu[0] = (View) menu;
		changeMenu[1] = (View) app;
		size = new SizeCallbackForMenu(slide);
		slideView.initViews(changeMenu, scrollToViewIdx, size);

		// 내가 받은 하트 세팅
		HeartListAdapter adapter = new HeartListAdapter(this,
				R.layout.heart_list_item, null);
		ListView listView = (ListView) heart.findViewById(R.id.heartList);
		listView.setAdapter(adapter);

		// 채팅 세팅
		ChatListAdapter chatAdapter = new ChatListAdapter(this,
				R.layout.chat_list_item, null);
		ListView chatlistView = (ListView) chat.findViewById(R.id.chatList);
		chatlistView.setAdapter(chatAdapter);
	}

	OnClickListener menuClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			ViewGroup parent = (ViewGroup) menu.getParent();
			parent.removeView(parent.getChildAt(1));

			switch (v.getId()) {
			case R.id.menuFind:
				Log.d("=== menuFind clicked ==", "clicked");
				changeMenu[1] = (View) app;
				break;
			case R.id.menuHeart:
				Log.d("=== menuHeart clicked ==", "clicked");
				changeMenu[1] = (View) heart;
				break;
			case R.id.menuChat:
				changeMenu[1] = (View) chat;
				break;
			case R.id.menuProfile:
				changeMenu[1] = (View) profile;
				break;
			case R.id.menuContact:
				changeMenu[1] = (View) contact;
				break;
			case R.id.menuSetting:
				changeMenu[1] = (View) setting;
				break;
			case R.id.menuStore:
				changeMenu[1] = (View) store;
				break;
			case R.id.menuNotice:
				changeMenu[1] = (View) notice;
				noticeView.loadUrl("http://m.naver.com/");
				break;
			case R.id.menuEvent:
				changeMenu[1] = (View) event;
				eventView.loadUrl("http://m.kitchen.naver.com/");
				break;
			case R.id.menuQna:
				changeMenu[1] = (View) qna;
				qnaView.loadUrl("https://m.help.naver.com/serviceMain.nhn?type=mail");
				break;
			case R.id.menuHelp:
				changeMenu[1] = (View) help;
				helpView.loadUrl("http://m.help.naver.com/serviceMain.nhn?falias=mo_main_web&type=faq");
				break;
			default:
				break;
			};

			parent.addView(changeMenu[1], slideView.getWidth(),
					slideView.getHeight());
			int menuWidth = slideView.getMeasuredWidth();
			slideView.smoothScrollTo(menuWidth, 0);

		}
	};

	static class MenuSlideListener implements OnClickListener {
		SlideView slideView;
		View menu, oldMenu;

		public MenuSlideListener(SlideView slideView, View menu, View oldMenu) {
			// TODO Auto-generated constructor stub
			this.slideView = slideView;
			this.menu = menu;
			this.oldMenu = oldMenu;
		}

		@Override
		public void onClick(View v) {

			Context context = menu.getContext();
			int menuWidth = menu.getMeasuredWidth();

			menu.setVisibility(View.VISIBLE);

			if (!menuOut) {
				// Scroll to 0 to reveal menu
				Log.d("===clicked 1==", "Scroll to right");
				int left = DeviceData.pxToDp(slideView.getContext(), 45);
				slideView.smoothScrollTo(left, 0);
			} else {
				// Scroll to menuWidth so menu isn't on screen.
				Log.d("===clicked 2==", "Scroll to left");
				int left = menuWidth;
				slideView.smoothScrollTo(left, 0);
			}
			menuOut = !menuOut;
		}

	}

	private void setMapTransparent(ViewGroup group) {
		int childCount = group.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = group.getChildAt(i);

			if (child instanceof ViewGroup) {
				setMapTransparent((ViewGroup) child);
			} else if (child instanceof SurfaceView) {
				child.setBackgroundColor(0x00000000);
				((SurfaceView) child).setZOrderOnTop(true);
			}
		}
	}

	private void initGmap() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		/*criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);*/
		provider = locationManager.getBestProvider(criteria, false);
		
		if (provider == null) {// 사용자가 위치설정동의 안했을 때
			new AlertDialog.Builder(this)
			   .setTitle("위치서비스 동의")
			   .setNeutralButton("이동",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,	int which) {
							startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);

						}
					}).setOnCancelListener(new DialogInterface.OnCancelListener() {
						
						@Override
						public void onCancel(DialogInterface dialog) {
							finish();
							
						}
					});

		}
		//locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
		Location location = locationManager.getLastKnownLocation(provider);
		try {
			myPos = new LatLng(location.getLatitude(), location.getLongitude());
		} catch (Exception e) {
			myPos = new LatLng((double)37.56621, (double)126.9779);
		}
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setAllGesturesEnabled(false);

		try {
			Log.d("ME:", "My Pos - latitude : " + location.getLatitude() + ", longitude :" + location.getLongitude());
		} catch (Exception e) {
		}

		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				Log.d("ME:", "Map Clicked!!!");

			}
		});
	}

	private final LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
			
		}
	};
	
	private void updateWithNewLocation(Location location){
        
        if(location != null){

            try {
            	myPos = new LatLng(location.getLatitude(), location.getLongitude());
            } catch (Exception e) {
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15));
        }

	} 
	
	
	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			// btnWidth = btnSlide.getMeasuredWidth();
			// System.out.println("btnWidth=" + btnWidth);
		}

		@Override
		public void getViewSize(int idx, int w, int h, int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - btnWidth;
			}
		}
	}
}
