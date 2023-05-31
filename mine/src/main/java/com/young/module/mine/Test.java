package com.young.module.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import com.young.utils.SizeUtils;
import com.young.view.dialog.MyAlertDialog;

public class Test {

    public static void test(Context context){
        MyAlertDialog privacyPolicyDialog = null;
        MyAlertDialog.Builder builder=new MyAlertDialog.Builder(context)
                .addDefaultAnimation()
                .setCancelable(true)
                .setContentView(R.layout.dialog_privacy_policy)
                .setWidthAndHeight(SizeUtils.dp2px(context, 280),
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(R.id.tv_privacy_policy, v -> {
//                    privacyPolicyDialog.dismiss();
//                    startActivity(new Intent(this, PrivacyPolicyActivity.class));
                }).setOnClickListener(R.id.tv_no_used, v -> {
//                    ToastUtils.showShortToast(context,"不同意隐私政策，无法正常使用App，请退出App，重新进入。");
//                    privacyPolicyDialog.dismiss();
                }).setOnClickListener(R.id.tv_agree, v -> {
                    //已同意隐私政策
//                    privacyPolicyDialog.dismiss();
                });
                ;
        privacyPolicyDialog = builder.create();
        privacyPolicyDialog.show();
    }

}
