package com.xx.chinetek.cywms.UpShelf;

import android.content.Context;
import android.widget.ListView;

import com.xx.chinetek.adapter.wms.Receiption.ReceiptBillDetailAdapter;
import com.xx.chinetek.base.BaseActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.base.ToolBarTitle;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.Receiption.ReceiptDetail_Model;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/*
没有用
 */
@ContentView(R.layout.activity_upshelf_bill_detail)
public class UpshelfBillDetail extends BaseActivity {

    Context context = UpshelfBillDetail.this;
    @ViewInject(R.id.lsvUpshelfDetail)
    ListView  lsvUpshelfDetail;

    @Override
    protected void initViews() {
        super.initViews();
        BaseApplication.context = context;
        BaseApplication.toolBarTitle = new ToolBarTitle( getString(R.string.UpShelfscan_billdetail), true);
        x.view().inject(this);
        List<ReceiptDetail_Model> receiptDetailModels=getData();
        ReceiptBillDetailAdapter receiptScanDetailAdapter=new ReceiptBillDetailAdapter(context,receiptDetailModels.get(0).getLstBarCode());
        lsvUpshelfDetail.setAdapter(receiptScanDetailAdapter);
    }

    List<ReceiptDetail_Model> getData(){
        List<ReceiptDetail_Model> receiptDetailModels=new ArrayList<>();
        for(int i=0;i<10;i++){
            ReceiptDetail_Model receiptDetailModel=new ReceiptDetail_Model();
            receiptDetailModel.setMaterialNo("123455"+i);
            receiptDetailModel.setMaterialDesc(context.getString(R.string.material_desc)+i);
            receiptDetailModel.setScanQty(1f);
            receiptDetailModel.setCompany(context.getString(R.string.stronghold));
            receiptDetailModel.setDepartment(context.getString(R.string.dept_item));
            receiptDetailModels.add(receiptDetailModel);
        }
        return receiptDetailModels;
    }
}
