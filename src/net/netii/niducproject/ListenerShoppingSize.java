package net.netii.niducproject;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ListenerShoppingSize implements OnClickListener {
	Context context;
	Button shoppingSizeBtn;
	static String sizes[] ={"S", "M", "L"};
	private Integer shoppingSize;

	public ListenerShoppingSize(Context context, Button shoppingSizeBtn, Integer shoppingSize) {
		super();
		this.context = context;
		this.shoppingSizeBtn = shoppingSizeBtn;
		this.shoppingSize = shoppingSize;
		setLabel();
	}

	private void setLabel() {
		shoppingSizeBtn.setText(sizes[shoppingSize]);
	}

	public Integer increment() {
		shoppingSize=(Integer) shoppingSize+1;
		shoppingSize%=3;
		setLabel();
		return shoppingSize;
	}

	@Override
	public void onClick(View v) {
		increment();
		Toast.makeText(context, shoppingSizeBtn.getText(), Toast.LENGTH_LONG).show();
	}

}
