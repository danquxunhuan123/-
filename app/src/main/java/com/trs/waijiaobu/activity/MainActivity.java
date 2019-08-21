package com.trs.waijiaobu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.trs.waijiaobu.Constant;
import com.trs.waijiaobu.R;
import com.trs.waijiaobu.fragment.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.radio_home_button)
    RadioGroup mRadioGroup;

    private FragmentFactory mFragmentFactory;
    private int checkId;
    private RadioButton oldButton;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] names = {"新闻", "领事", "资料", "服务", "微博"};
    private int[] drawables = {R.drawable.menu_xinwen, R.drawable.menu_lingshi, R.drawable.menu_ziliao,
            R.drawable.menu_fuwu, R.drawable.menu_weibo};

    private int[] drawables_h = {R.drawable.menu_xinwen_h, R.drawable.menu_lingshi_h, R.drawable.menu_ziliao_h,
            R.drawable.menu_fuwu_h, R.drawable.menu_weibo_h};

    @Override
    protected void initView() {
        mFragmentFactory = new FragmentFactory.FragmentCreator();
        initFragmet();
        initRadioGroup();
    }

    @Override
    protected void getData() {
        requestPerssions();
    }

    @Override
    protected int flateLayout() {
        return R.layout.activity_main;
    }

    private void initRadioGroup() {
        mRadioGroup.removeAllViews();
        int dp5 = SizeUtils.px2dp(5);
        for (int i = 0; i < names.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioButton.setPadding(dp5, dp5, dp5, dp5);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setButtonDrawable(null);
            radioButton.setText(names[i]);
            radioButton.setTextColor(Color.BLACK);
            radioButton.setCompoundDrawablePadding(dp5);
            Drawable drawable = getResources().getDrawable(drawables[i]);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            radioButton.setCompoundDrawables(null, drawable, null, null);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    0, RadioGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            mRadioGroup.addView(radioButton, i, params);

            if (i == 0) {
                checkId = i;
                mRadioGroup.check(checkId);
                oldButton = radioButton;

                Drawable drawable_h = getResources().getDrawable(drawables_h[i]);
                drawable_h.setBounds(0, 0, drawable_h.getMinimumWidth(),
                        drawable_h.getMinimumHeight());
                oldButton.setCompoundDrawables(null, drawable_h, null, null);
                oldButton.setTextColor(Color.parseColor("#1a7fc8"));
            }
        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                oldButton.setTextColor(Color.BLACK);
                Drawable drawable = getResources().getDrawable(drawables[checkId]);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                oldButton.setCompoundDrawables(null, drawable, null, null);

                RadioButton checkButton = (RadioButton) group.getChildAt(checkedId);
                checkButton.setTextColor(Color.parseColor("#1a7fc8"));
                Drawable drawable_h = getResources().getDrawable(drawables_h[checkedId]);
                drawable_h.setBounds(0, 0, drawable_h.getMinimumWidth(),
                        drawable_h.getMinimumHeight());
                checkButton.setCompoundDrawables(null, drawable_h, null, null);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.hide(getSupportFragmentManager().findFragmentByTag(String.valueOf(checkId)));
                Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(String.valueOf(checkedId));
                if (fragmentByTag == null) {
                    switch (checkedId) {
                        case 1:
                            ft.add(R.id.fl_content, mFragmentFactory.getFragment(checkedId), String.valueOf(checkedId));
                            break;
                        case 2:
                            ft.add(R.id.fl_content, mFragmentFactory.getFragment(checkedId), String.valueOf(checkedId));
                            break;
                        case 3:
                            ft.add(R.id.fl_content, mFragmentFactory.getFragment(checkedId), String.valueOf(checkedId));
                            break;
                        case 4:
                            ft.add(R.id.fl_content, mFragmentFactory.getFragment(checkedId), String.valueOf(checkedId));
                            break;
                    }
                } else
                    ft.show(fragmentByTag);

                ft.commit();

                oldButton = checkButton;
                checkId = checkedId;
            }
        });
    }

    private void initFragmet() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(String.valueOf(0));
        if (fragmentByTag == null) {
            fragment = mFragmentFactory.getFragment(0);
            ft.add(R.id.fl_content, fragment, String.valueOf(0));
        } else {
            fragment = fragmentByTag;
        }
        ft.show(fragment);
        ft.commit();

    }

    private void requestPerssions() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {

//                ToastUtils.showShort("granted");
            }

            @Override
            public void onDenied() {
//                ToastUtils.showShort("denied");
            }
        }).request();
    }

    public void msg(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(ListActivity.ARG_PARAM1, Constant.PUSH_MSG);
        startActivity(intent);
    }
}
