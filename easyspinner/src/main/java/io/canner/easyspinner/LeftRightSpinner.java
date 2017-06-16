package io.canner.easyspinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import io.canner.easyspinner.adapter.NothingSelectedSpinnerAdapter;

/**
 * Created by lijung on 6/16/17.
 */

public class LeftRightSpinner extends SpinnerLayout {
    private String title = "";
    private int entriesResId;
    private int titleStyleResId;
    private int spinnerMode;
    private int nothingSelectedLayout;
    private int itemLayout;
    private int itemSelectedLayout;
    private String promptText;

    public LeftRightSpinner(Context context ) {
        this(context, null);
    }

    public LeftRightSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftRightSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray arr = context.obtainStyledAttributes(attrs, io.canner.easyspinner.R.styleable.LeftRightSpinner);
        CharSequence title = arr.getString(io.canner.easyspinner.R.styleable.LeftRightSpinner_title);
        int entriesResId = arr.getResourceId(io.canner.easyspinner.R.styleable.LeftRightSpinner_entries, 0);
        this.titleStyleResId = arr.getResourceId(io.canner.easyspinner.R.styleable.LeftRightSpinner_titleTextStyle, io.canner.easyspinner.R.style.innerText);
        this.spinnerMode = arr.getInt(io.canner.easyspinner.R.styleable.LeftRightSpinner_spinnerMode, 0);
        this.nothingSelectedLayout = arr.getResourceId(io.canner.easyspinner.R.styleable.LeftRightSpinner_nothingSelectedLayout, io.canner.easyspinner.R.layout.spinner_row_nothing_selected);
        this.itemLayout = arr.getResourceId(io.canner.easyspinner.R.styleable.LeftRightSpinner_itemLayout, io.canner.easyspinner.R.layout.spinner_item);
        this.itemSelectedLayout = arr.getResourceId(io.canner.easyspinner.R.styleable.LeftRightSpinner_itemSelectedLayout, io.canner.easyspinner.R.layout.spinner_item_selected);
        CharSequence promptText = arr.getString(io.canner.easyspinner.R.styleable.LeftRightSpinner_prompt);

        if (title != null) {
            this.title = title.toString();
        }

        if (promptText != null) {
            this.promptText = promptText.toString();
        } else {
            this.promptText = this.title;
        }

        if (entriesResId != 0) {
            this.entriesResId = entriesResId;
        }

        arr.recycle();  // Do this when done.
        init();
    }

    private void init() {
        TextView titleView = new TextView(getContext());
        Spinner spinnerView = new Spinner(getContext(), spinnerMode);
        this.spinnerView = spinnerView;
        setTextViewTextAppearance(titleView, titleStyleResId);
        // setup title view and add to relativelayout.
        titleView.setText(title);
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        titleView.setLayoutParams(titleLayoutParams);
        addView(titleView);

        // setup spinner view and add to view
        spinnerView.setPrompt(promptText);
        RelativeLayout.LayoutParams spinnerLayoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        spinnerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        spinnerView.setLayoutParams(spinnerLayoutParams);
        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(getContext(), entriesResId, this.itemSelectedLayout);
        spinnerArrayAdapter.setDropDownViewResource(this.itemLayout);
        spinnerView.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        spinnerArrayAdapter,
                        nothingSelectedLayout,
                        getContext()
                )
        );
        addView(spinnerView);
    }
}
