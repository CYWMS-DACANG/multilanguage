//package com.xx.chinetek.cywms;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.GridView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.xx.chinetek.Box.Boxing;
//import com.xx.chinetek.FillPrint.FillPrint;
//import com.xx.chinetek.Pallet.CombinPallet;
//import com.xx.chinetek.Pallet.DismantlePallet;
//import com.xx.chinetek.adapter.GridViewItemAdapter;
//import com.xx.chinetek.base.BaseActivity;
//import com.xx.chinetek.base.BaseApplication;
//import com.xx.chinetek.base.ToolBarTitle;
//import com.xx.chinetek.cywms.InnerMove.InnerMoveScan;
//import com.xx.chinetek.cywms.Intentory.InventoryBillChoice;
//import com.xx.chinetek.cywms.MaterialChange.MaterialChangeReceiptBillChoice;
//import com.xx.chinetek.cywms.OffShelf.OffShelfBillChoice;
//import com.xx.chinetek.cywms.Qc.QCBillChoice;
//import com.xx.chinetek.cywms.Query.QueryMain;
//import com.xx.chinetek.cywms.Receiption.ReceiptBillChoice;
//import com.xx.chinetek.cywms.Review.ReviewBillChoice;
//import com.xx.chinetek.cywms.Stock.AdjustStock;
//import com.xx.chinetek.cywms.UpShelf.UpShelfBillChoice;
//import com.xx.chinetek.model.User.MenuInfo;
//import com.xx.chinetek.util.function.CommonUtil;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//import org.xutils.x;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@ContentView(R.layout.activity_main)
//public class MainActivity extends BaseActivity {
//
//    @ViewInject(R.id.gv_Function)
//    GridView gridView;
//    GridViewItemAdapter adapter;
//    Context context = MainActivity.this;
//
//    @Override
//    protected void initViews() {
//        super.initViews();
//        BaseApplication.context = context;
//        BaseApplication.toolBarTitle = new ToolBarTitle(getString(R.string.app_name),false);
//        x.view().inject(this);
//        List<Map<String, Object>> data_list = getData();
//        adapter = new GridViewItemAdapter(context,data_list);
//        gridView.setAdapter(adapter);
//    }
//
//
//    @Event(value = R.id.gv_Function,type = AdapterView.OnItemClickListener.class)
//    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        LinearLayout linearLayout=(LinearLayout) gridView.getAdapter().getView(position,view,null);
//        TextView textView=(TextView)linearLayout.getChildAt(1);
//        Intent intent = new Intent();
//        if(textView.getText().toString().equals(context.getString(R.string.QC_title)))
//            intent.setClass(context, QCBillChoice.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.receipt_title)))
//            intent.setClass(context, ReceiptBillChoice.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.UpShelf_title)))
//            intent.setClass(context, UpShelfBillChoice.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.OffShelf_title)))
//            intent.setClass(context, OffShelfBillChoice.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.Review_title)))
//            intent.setClass(context, ReviewBillChoice.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.InnerMove_title)))
//            intent.setClass(context, InnerMoveScan.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.Intentory_title))) {
//            intent.setClass(context, InventoryBillChoice.class);
//            intent.putExtra("model",1);
//        }
//        else if(textView.getText().toString().equals(context.getString(R.string.financial_inventory))) {
//            intent.setClass(context, InventoryBillChoice.class);
//            intent.putExtra("model",2);
//        }
//        else if(textView.getText().toString().equals(context.getString(R.string.query_title)))
//            intent.setClass(context, QueryMain.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.Pallet_title)))
//            intent.setClass(context, CombinPallet.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.DisPallet_title)))
//            intent.setClass(context, DismantlePallet.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.pack_unpack)))
//            intent.setClass(context, Boxing.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.material_transfer)))
//            intent.setClass(context, MaterialChangeReceiptBillChoice.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.patch_print)))
//            intent.setClass(context, FillPrint.class);
//        else if(textView.getText().toString().equals(context.getString(R.string.adjust_title)))
//            intent.setClass(context, AdjustStock.class);
//        if(intent!=null)
//            startActivityLeft(intent);
//    }
//
//
//    @Override
//    protected void initData() {
//        super.initData();
//
//    }
//
//    public List<Map<String, Object>> getData(){
//        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
//        ArrayList<Integer>  itemIconList=new ArrayList<>();
//        ArrayList<String>  itemNamesList=new ArrayList<>();
//        List<MenuInfo> menuInfos=BaseApplication.userInfo.getLstMenu();
//        if(menuInfos!=null) {
//            for (int i = 0; i < menuInfos.size(); i++) {
//                String nodUrl = menuInfos.get(i).getNodeUrl();
//                if(!CommonUtil.isNumeric(nodUrl)) continue;
//                int Node = Integer.parseInt(nodUrl);
//                switch (Node) {
//                    case 1:
//                        itemIconList.add(R.drawable.qc);
//                        itemNamesList.add(context.getString(R.string.QC_title));
//                        break;
//                    case 2:
//                        itemIconList.add(R.drawable.receiption);
//                        itemNamesList.add(context.getString(R.string.receipt_title));
//                        break;
//                    case 3:
//                        itemIconList.add(R.drawable.upshelves);
//                        itemNamesList.add(context.getString(R.string.UpShelf_title));
//                        break;
//                    case 4:
//                        itemIconList.add(R.drawable.offshelf);
//                        itemNamesList.add(context.getString(R.string.OffShelf_title));
//                        break;
//                    case 5:
//                        itemIconList.add(R.drawable.review);
//                        itemNamesList.add(context.getString(R.string.Review_title));
//                        break;
//                    case 6:
//                        itemIconList.add(R.drawable.innermove);
//                        itemNamesList.add(context.getString(R.string.InnerMove_title));
//                        break;
//                    case 7:
//                        itemIconList.add(R.drawable.inventory);
//                        itemNamesList.add(context.getString(R.string.Intentory_title));
//                        itemIconList.add(R.drawable.intentoryfinc);
//                        itemNamesList.add(context.getString(R.string.financial_inventory));
//                        break;
//                    case 8:
//                        itemIconList.add(R.drawable.query);
//                        itemNamesList.add(context.getString(R.string.query_title));
//                        break;
//                    case 9:
//                        itemIconList.add(R.drawable.combinepallet);
//                        itemNamesList.add(context.getString(R.string.Pallet_title));
//                        break;
//                    case 10:
//                        itemIconList.add(R.drawable.dismantlepallet);
//                        itemNamesList.add(context.getString(R.string.DisPallet_title));
//                        break;
//                    case 11:
//                        itemIconList.add(R.drawable.dismounting);
//                        itemNamesList.add(context.getString(R.string.pack_unpack));
//                        break;
//                    case 12:
//                        itemIconList.add(R.drawable.materiel);
//                        itemNamesList.add(context.getString(R.string.material_transfer));
//                        break;
//                    case 13:
//                        itemIconList.add(R.drawable.fillprint);
//                        itemNamesList.add(context.getString(R.string.patch_print));
//                        break;
//                    case 14:
//                        itemIconList.add(R.drawable.adjustment);
//                        itemNamesList.add(context.getString(R.string.adjust_title));
//                        break;
//                }
//            }
//            //cion和iconName的长度是相同的，这里任选其一都可以
//            for (int i = 0; i < itemIconList.size(); i++) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("image", itemIconList.get(i));
//                map.put("text", itemNamesList.get(i));
//                data_list.add(map);
//            }
//        }
//
//        return data_list;
//    }
//}
