package com.src.appsonite;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CompanyActivity extends MapActivity {

	private MapView mapView;
	private MapController mapController;
	private View popView;
	private OverlayItem localItem;
	GeoPoint geoPoint;

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_layout);
		
		LayoutInflater inflate = this.getLayoutInflater();
		popView = inflate.inflate(R.layout.map_pop_view, null);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.displayZoomControls(true);
		mapView.setSatellite(false);
		mapController = mapView.getController();
		mapController.setZoom(16);
		
		double lat = 59.913195;
		double lng = 10.743576;

		geoPoint = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		mapController.setCenter(geoPoint);
		localItem = new OverlayItem(geoPoint, "Oslo", "Nedre Slottsgate 21");
		
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.mylocation);
		CustomItemizedOverlay citem = new CustomItemizedOverlay(drawable);
		citem.setOnFocusChangeListener(onFocusChangeListener);
		
		localItem.setMarker(drawable);
		citem.addOverlay(localItem);
		
		mapView.getOverlays().add(citem);
		
		mapView.addView(popView, new MapView.LayoutParams(
				MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.BOTTOM_CENTER));
		popView.setVisibility(View.GONE);
		mapView.invalidate();
	}

	class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem> {
		private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

		public ArrayList<OverlayItem> getItems() {
			return items;
		}

		public void setItems(ArrayList<OverlayItem> items) {
			this.items = items;
		}

		private Drawable marker = null;

		public CustomItemizedOverlay(Drawable defaultMarker) {
			super(defaultMarker);
			this.marker = defaultMarker;
		}

		public void addOverlay(OverlayItem item) {
			items.add(item);
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return items.get(i);
		}

		@Override
		public int size() {
			return items.size();
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			boundCenterBottom(marker);
		}

	}
	
	private final ItemizedOverlay.OnFocusChangeListener onFocusChangeListener = new ItemizedOverlay.OnFocusChangeListener() {
		@Override
		public void onFocusChanged(@SuppressWarnings("rawtypes") ItemizedOverlay overlay, OverlayItem newFocus) {
			if (popView != null) {
				popView.setVisibility(View.GONE);
			}
			if (newFocus != null) {
				newFocus.setMarker(null);
				MapView.LayoutParams mapLayoutParams = (MapView.LayoutParams) popView.getLayoutParams();
				mapLayoutParams.point = geoPoint;
				TextView title = (TextView) popView.findViewById(R.id.map_bubbleTitle);
				title.setTextSize(12);
				title.setText(newFocus.getTitle());
				TextView desc = (TextView) popView
						.findViewById(R.id.map_bubbleText);
				final ImageView floorImg = (ImageView) popView
						.findViewById(R.id.map_bubbleIcon);
				floorImg.setVisibility(View.GONE);
	
				
				if (localItem != null) {
					if (newFocus.hashCode() == localItem.hashCode()) {
						floorImg.setVisibility(View.GONE);
					}
				}
				if (newFocus.getSnippet() == null
						|| newFocus.getSnippet().length() == 0) {
					desc.setVisibility(View.GONE);
				} else {
					desc.setVisibility(View.VISIBLE);
					desc.setTextSize(12);
					desc.setText(newFocus.getSnippet());
					
				}
				mapView.updateViewLayout(popView, mapLayoutParams);
				popView.setVisibility(View.VISIBLE);
			}
		}
	};
}
