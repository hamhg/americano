package com.example.meeting79;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
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
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.meeting79.SlideView.SizeCallback;
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

	boolean click;
	private GoogleMap map;
	
	SlideView slideView;
	View menu, app, heart;
	View[] changeMenu = new View[2];
	SizeCallbackForMenu size;
	
	static boolean menuOut = false;
	int btnWidth;
	Button slide, slideHeart;

	LinearLayout menuFind, menuHeart, menuChat, menuProfile, menuContact, menuSetting, menuStore, menuNotice, menuEvent, menuQna, menuHelp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        if( Build.VERSION.SDK_INT >= 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy); 
        }
        
		LayoutInflater inflater = LayoutInflater.from(this);
		slideView = (SlideView) inflater.inflate(R.layout.slide_view, null);
        setContentView(slideView);
        
        menu = inflater.inflate(R.layout.leftmenu, null);
        app = inflater.inflate(R.layout.activity_match, null);
        heart = inflater.inflate(R.layout.activity_heart, null);
        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
        ViewGroup tabBarHeart = (ViewGroup) heart.findViewById(R.id.tabBarHeart);
        
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
        
        slide = (Button) tabBar.findViewById(R.id.slide);
        slideHeart = (Button) tabBarHeart.findViewById(R.id.slideHeart);

        slide.setOnClickListener(new MenuSlideListener(slideView, menu));
        slideHeart.setOnClickListener(new MenuSlideListener(slideView, menu));

		
        setMapTransparent((ViewGroup) app);
        
        final View[] children = new View[] { menu, app };      
        int scrollToViewIdx = 1;
        
        changeMenu[0] = (View) menu;

        size = new SizeCallbackForMenu(slide);
        
        slideView.initViews(children, scrollToViewIdx, size); 
        initGmap();
        
	}

	OnClickListener menuClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menuFind:
				Log.d("=== menuFind clicked ==","clicked");
				changeMenu[1] = (View) app;
				break;
			case R.id.menuHeart:
				Log.d("=== menuHeart clicked ==","clicked");
				changeMenu[1] = (View) heart;
				break;
			case R.id.menuChat:
				
				break;
			case R.id.menuProfile:
				
				break;
			case R.id.menuContact:
				
				break;
			case R.id.menuSetting:
				
				break;
			case R.id.menuStore:
				
				break;
			case R.id.menuNotice:
				
				break;
			case R.id.menuEvent:
				
				break;
			case R.id.menuQna:
				
				break;
			case R.id.menuHelp:
				
				break;
			default:
				break;
			};
			slideView.initViews(changeMenu, 1, size);
		}
	};
	
	static class MenuSlideListener implements OnClickListener {
		SlideView slideView;
		View menu;
		
		public MenuSlideListener(SlideView slideView, View menu) {
			// TODO Auto-generated constructor stub
			this.slideView = slideView;
			this.menu = menu;
		}
		
		@Override
		public void onClick(View v) {
			
			 Context context = menu.getContext();
			 int menuWidth = menu.getMeasuredWidth();
			 
			 menu.setVisibility(View.VISIBLE);
			 
			 if (!menuOut) {
	                // Scroll to 0 to reveal menu
	            	Log.d("===slide 1==","Scroll to right");
	            	Log.d("===clicked 1==","clicked");
	                int left = 70;
	                slideView.smoothScrollTo(left, 0);
	            } else {
	                // Scroll to menuWidth so menu isn't on screen.
	            	Log.d("===slide 2==","Scroll to left");
	            	Log.d("===clicked 2==","clicked");
	                int left = menuWidth;
	                slideView.smoothScrollTo(left, 0);
	            }
	            menuOut = !menuOut;		
		}
		

	}
	
	private void setMapTransparent(ViewGroup group) 
	{
	    int childCount = group.getChildCount();
	    for (int i = 0; i < childCount; i++) 
	    {
	        View child = group.getChildAt(i);

	        if (child instanceof ViewGroup)
	        {
	            setMapTransparent((ViewGroup) child);
	        } 
	        else if (child instanceof SurfaceView) 
	        {
	            child.setBackgroundColor(0x00000000);
	            ((SurfaceView) child).setZOrderOnTop(true);
	        }
	    }
	}

	private void initGmap(){
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		myPos = new LatLng(location.getLatitude(), location.getLongitude());

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setAllGesturesEnabled(false);
		
		Log.d("ME:", "My Pos - latitude : " + location.getLatitude() + ", longitude :" + location.getLongitude());

		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				Log.d("ME:", "Map Clicked!!!");

			}
		});
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
            //btnWidth = btnSlide.getMeasuredWidth();
            //System.out.println("btnWidth=" + btnWidth);
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
