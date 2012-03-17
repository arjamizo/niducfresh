package net.netii.niducproject;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ListenerShoppingSize implements OnClickListener {
	Context context;
	Button shoppingSize;
	static String sizes[] ={"S", "M", "L"};

	public ListenerShoppingSize(Context context, Button shoppingSize) {
		super();
		this.context = context;
		this.shoppingSize = shoppingSize;
		shoppingSize.setTag(new Integer(0));
		setLabel();
	}

	private void setLabel() {
		Integer next=(Integer) shoppingSize.getTag()+1;
		next%=3;
		shoppingSize.setTag(next);
		shoppingSize.setText(sizes[next]);
	}

	@Override
	public void onClick(View v) {
		setLabel();
		Toast.makeText(context, shoppingSize.getText(), Toast.LENGTH_LONG).show();
	}

}
