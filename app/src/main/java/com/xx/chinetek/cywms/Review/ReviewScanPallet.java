package com.xx.chinetek.cywms.Review;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.xx.chinetek.adapter.wms.Pallet.PalletItemAdapter;
import com.xx.chinetek.base.BaseActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.base.ToolBarTitle;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.Base_Model;
import com.xx.chinetek.model.Material.BarCodeInfo;
import com.xx.chinetek.model.Pallet.PalletDetail_Model;
import com.xx.chinetek.model.ReturnMsgModel;
import com.xx.chinetek.model.ReturnMsgModelList;
import com.xx.chinetek.model.URLModel;
import com.xx.chinetek.model.WMS.Inventory.Barcode_Model;
import com.xx.chinetek.util.Network.NetworkError;
import com.xx.chinetek.util.Network.RequestHandler;
import com.xx.chinetek.util.dialog.MessageBox;
import com.xx.chinetek.util.dialog.ToastUtil;
import com.xx.chinetek.util.function.ArithUtil;
import com.xx.chinetek.util.function.CommonUtil;
import com.xx.chinetek.util.function.DoubleClickCheck;
import com.xx.chinetek.util.function.GsonUtil;
import com.xx.chinetek.util.log.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xx.chinetek.cywms.R.id.SW_Pallet;
import static com.xx.chinetek.util.function.GsonUtil.parseModelToJson;

@ContentView(R.layout.activity_review_pallet)
public class ReviewScanPallet extends BaseActivity {

    String TAG_GetT_PalletDetailByNoADF="ReviewPallet_GetT_PalletDetailByNoADF";
    Context context=ReviewScanPallet.this;
    private final int RESULT_GetT_PalletDetailByNoADF = 101;

    boolean isBarcodeScaned=false;

    @Override
    public void onHandleMessage(Message msg) {
        switch (msg.what) {
            case RESULT_GetT_PalletDetailByNoADF:
                AnalysisGetT_PalletAD((String) msg.obj);
                break;
            case NetworkError.NET_ERROR_CUSTOM:
                ToastUtil.show(context.getString(R.string.get_request_failed)+ msg.obj);
                break;
        }
    }


    @ViewInject(R.id.txt_Company)
    TextView txtCompany;
    @ViewInject(R.id.txt_Batch)
    TextView txtBatch;
    @ViewInject(R.id.txt_Status)
    TextView txtStatus;
    @ViewInject(R.id.txt_EDate)
    TextView txtEDate;
    @ViewInject(R.id.txt_MaterialName)
    TextView txtMaterialName;
    @ViewInject(R.id.txt_CartonNum)
    TextView txtCartonNum;
    @ViewInject(R.id.edt_Barcode)
    EditText edtBarcode;
    @ViewInject(R.id.lsv_PalletDetail)
    ListView lsvPalletDetail;
    @ViewInject(R.id.btn_PrintPalletLabel)
    Button btnPrintPalletLabel;

    PalletItemAdapter palletItemAdapter;
    List<PalletDetail_Model> palletDetailModels;

    String Palletno="";

    @Override
    protected void initViews() {
        super.initViews();
        BaseApplication.context = context;
        BaseApplication.toolBarTitle = new ToolBarTitle( getString(R.string.review_Pallet_scan), false);
        Palletno=getIntent().getStringExtra("Palletno");
        x.view().inject(this);
        BaseApplication.isCloseActivity=false;
    }

    @Override
    protected void initData() {
        super.initData();
        GetPalletDetail(Palletno);
    }


    private void GetPalletDetail(String code){
        isBarcodeScaned=false;
        final Map<String, String> params = new HashMap<String, String>();
        params.put("Barcode", code);
        params.put("PalletModel","2"); //1：新建托盘  2：插入组托
        LogUtil.WriteLog(ReviewScanPallet.class, TAG_GetT_PalletDetailByNoADF, code);
        RequestHandler.addRequestWithDialog(Request.Method.POST, TAG_GetT_PalletDetailByNoADF, getString(R.string.Msg_GetT_PalletADF), context, mHandler, RESULT_GetT_PalletDetailByNoADF, null,  URLModel.GetURL().GetT_PalletDetailByNoADF, params, null);

    }

    @Event(value = R.id.edt_Barcode,type = View.OnKeyListener.class)
    private  boolean edtBarcodeonKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)// 如果为Enter键
        {
            CommonUtil.setEditFocus(edtBarcode);

            String barcode=edtBarcode.getText().toString().trim();
            if (palletDetailModels!=null&&palletDetailModels.get(0).getLstBarCode()!=null){
                for (int i=0;i<palletDetailModels.get(0).getLstBarCode().size();i++){
                    if (palletDetailModels.get(0).getLstBarCode().get(i).getBarCode().equals(barcode)||palletDetailModels.get(0).getLstBarCode().get(i).getSerialNo().equals(barcode)){
                        palletDetailModels.get(0).getLstBarCode().get(i).setLabelMark("1");
                        break;
                    }
                }
            }
            BindListVIew(palletDetailModels.get(0).getLstBarCode());
            edtBarcode.setText("");
            CommonUtil.setEditFocus(edtBarcode);
        }
        return false;
    }

    @Event(R.id.btn_PrintPalletLabel)
    private void btnPrintPalletLabelClick(View v) {
        if (DoubleClickCheck.isFastDoubleClick(context)) {
            return;
        }
        close();
    }

    void close(){
        Intent mIntent = new Intent();
        ArrayList<String> serialnos = new ArrayList<String>();
        try{
            for (int i=0;i<palletDetailModels.get(0).getLstBarCode().size();i++){
                serialnos.add(palletDetailModels.get(0).getLstBarCode().get(i).getSerialNo());
            }
            mIntent.putExtra("Serialno",parseModelToJson(serialnos));
            setResult(RESULT_FIRST_USER, mIntent);
            closeActiviry();
        }catch (Exception ex){
            MessageBox.Show(context,ex.toString());
        }

    }

    /*
    解析托盘条码扫描
     */
    void AnalysisGetT_PalletAD(String result){
        LogUtil.WriteLog(ReviewScanPallet.class, TAG_GetT_PalletDetailByNoADF,result);
        ReturnMsgModelList<PalletDetail_Model> returnMsgModel =  GsonUtil.getGsonUtil().fromJson(result, new TypeToken<ReturnMsgModelList<PalletDetail_Model>>() {}.getType());
        if(returnMsgModel.getHeaderStatus().equals("S")){
            palletDetailModels=returnMsgModel.getModelJson();
            if(palletDetailModels!=null) {
                BindListVIew(palletDetailModels.get(0).getLstBarCode());
                txtCartonNum.setText(ShowNum());
            }
            CommonUtil.setEditFocus(edtBarcode);
        }else{
            MessageBox.Show(context,returnMsgModel.getMessage());
            CommonUtil.setEditFocus(edtBarcode);
        }

    }


    private void BindListVIew(List<BarCodeInfo> barCodeInfos) {
            palletItemAdapter = new PalletItemAdapter(context, barCodeInfos);
            lsvPalletDetail.setAdapter(palletItemAdapter);
    }


    Float GetAllQunantity(){
        Float sumPackageQty=0f;
        if(palletDetailModels.get(0).getLstBarCode()!=null) {
            for (BarCodeInfo barCodeInfo : palletDetailModels.get(0).getLstBarCode()) {
                sumPackageQty = ArithUtil.add(sumPackageQty,barCodeInfo.getQty());
            }
        }
       return sumPackageQty;
    }

    String ShowNum(){
        return new StringBuffer().append(palletDetailModels.get(0).getLstBarCode().size()).append(" / ").append(GetAllQunantity()).toString();
    }



}
