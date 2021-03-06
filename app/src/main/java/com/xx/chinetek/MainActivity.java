package com.xx.chinetek;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xx.chinetek.Box.Boxing;
import com.xx.chinetek.FillPrint.FillPrint;
import com.xx.chinetek.Pallet.CombinPallet;
import com.xx.chinetek.Pallet.CombinPalletSupplier;
import com.xx.chinetek.Pallet.DismantlePallet;
import com.xx.chinetek.adapter.GridViewItemAdapter;
import com.xx.chinetek.base.BaseActivity;
import com.xx.chinetek.base.BaseApplication;
import com.xx.chinetek.base.ToolBarTitle;
import com.xx.chinetek.cyproduct.Adjust.AdjustCP;
import com.xx.chinetek.cyproduct.Billinstock.BillsIn;
import com.xx.chinetek.cyproduct.LineStockIn.LineStockInMaterial;
import com.xx.chinetek.cyproduct.LineStockIn.LineStockInProduct;
import com.xx.chinetek.cyproduct.LineStockOut.LineStockOutProduct;
import com.xx.chinetek.cyproduct.LineStockOut.LineStockOutReturnBillChoice;
import com.xx.chinetek.cyproduct.LineStockOut.Zcj;
import com.xx.chinetek.cyproduct.Manage.LineManage;
import com.xx.chinetek.cyproduct.WoBillChoice;
import com.xx.chinetek.cywms.InnerMove.InnerMoveScan;
import com.xx.chinetek.cywms.Intentory.InventoryBillChoice;
import com.xx.chinetek.cywms.MaterialChange.MaterialChangeReceiptBillChoice;
import com.xx.chinetek.cywms.OffShelf.OffShelfBillChoice;
import com.xx.chinetek.cywms.Qc.QCBillChoice;
import com.xx.chinetek.cywms.Query.QueryMain;
import com.xx.chinetek.cywms.R;
import com.xx.chinetek.cywms.Receiption.ReceiptBillChoice;
import com.xx.chinetek.cywms.Review.ReviewBillChoice;
import com.xx.chinetek.cywms.Stock.AdjustStock;
import com.xx.chinetek.cywms.UpShelf.UpShelfBillChoice;
import com.xx.chinetek.model.User.MenuInfo;
import com.xx.chinetek.util.function.CommonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.gv_Function)
    GridView gridView;
    GridViewItemAdapter adapter;
    Context             context = MainActivity.this;

    @Override
    protected void initViews() {
        super.initViews();
        BaseApplication.context = context;
        BaseApplication.toolBarTitle = new ToolBarTitle(context.getString(R.string.app_name) + "-" + BaseApplication.userInfo.getWarehouseName(), false);
        x.view().inject(this);
        List<Map<String, Object>> data_list = getData();
        adapter = new GridViewItemAdapter(context, data_list);
        gridView.setAdapter(adapter);
    }


    @Event(value = R.id.gv_Function, type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout linearLayout = (LinearLayout) gridView.getAdapter().getView(position, view, null);
        TextView textView = (TextView) linearLayout.getChildAt(1);
        Intent intent = new Intent();
        if (textView.getText().toString().equals(context.getString(R.string.QC_title)))
            intent.setClass(context, QCBillChoice.class);
        else if (textView.getText().toString().equals(context.getString(R.string.receipt_title)))
            intent.setClass(context, ReceiptBillChoice.class);
        else if (textView.getText().toString().equals(context.getString(R.string.UpShelf_title)))
            intent.setClass(context, UpShelfBillChoice.class);
        else if (textView.getText().toString().equals(context.getString(R.string.OffShelf_title)))
            intent.setClass(context, OffShelfBillChoice.class);
        else if (textView.getText().toString().equals(context.getString(R.string.Review_title)))
            intent.setClass(context, ReviewBillChoice.class);
        else if (textView.getText().toString().equals(context.getString(R.string.InnerMove_title)))
            intent.setClass(context, InnerMoveScan.class);
        else if (textView.getText().toString().equals(context.getString(R.string.Intentory_title))) {
            intent.setClass(context, InventoryBillChoice.class);
            intent.putExtra("model", 1);
        } else if (textView.getText().toString().equals(getString(R.string.financial_inventory))) {
            intent.setClass(context, InventoryBillChoice.class);
            intent.putExtra("model", 2);
        } else if (textView.getText().toString().equals(context.getString(R.string.query_title)))
            intent.setClass(context, QueryMain.class);
        else if (textView.getText().toString().equals(context.getString(R.string.Pallet_title)))
            intent.setClass(context, CombinPallet.class);
        else if (textView.getText().toString().equals(context.getString(R.string.DisPallet_title)))
            intent.setClass(context, DismantlePallet.class);
        else if (textView.getText().toString().equals(context.getString(R.string.pack_unpack)))
            intent.setClass(context, Boxing.class);
        else if (textView.getText().toString().equals(context.getString(R.string.material_transfer)))
            intent.setClass(context, MaterialChangeReceiptBillChoice.class);
        else if (textView.getText().toString().equals(context.getString(R.string.patch_print)))
            intent.setClass(context, FillPrint.class);
        else if (textView.getText().toString().equals(context.getString(R.string.adjust_title)))
            intent.setClass(context, AdjustStock.class);
        else if (textView.getText().toString().equals(context.getString(R.string.Product_Product_adjustCPYMH)))
            intent.setClass(context, AdjustCP.class);
        else if (textView.getText().toString().equals(context.getString(R.string.Product_ProductStockin_subtitleYMH)))
            intent.setClass(context, LineStockInProduct.class);
        else if (textView.getText().toString().equals(context.getString(R.string.LineStockInMaterialY)))
            intent.setClass(context, LineStockInMaterial.class);
        else if (textView.getText().toString().equals(context.getString(R.string.LineStockInReturnBillChoice))) {
            BaseApplication.toolBarTitle = new ToolBarTitle(getString(R.string.LineStockInReturnBillChoice), true);
            intent.setClass(context, WoBillChoice.class);
        } else if (textView.getText().toString().equals(context.getString(R.string.LineStockOutReturnBillChoice)))
            intent.setClass(context, LineStockOutReturnBillChoice.class);
        else if (textView.getText().toString().equals(context.getString(R.string.Product_ProductStockout_subtitleYMH)))
            intent.setClass(context, LineStockOutProduct.class);
        else if (textView.getText().toString().equals(context.getString(R.string.LineStockOutMaterial))) {
            BaseApplication.toolBarTitle = new ToolBarTitle(getString(R.string.LineStockOutMaterial), true);
            intent.setClass(context, WoBillChoice.class);
        } else if (textView.getText().toString().equals(context.getString(R.string.Product_ProductYMH)))
            intent.setClass(context, BillsIn.class);
        else if (textView.getText().toString().equals(context.getString(R.string.Product_manage_subtitle)))
            intent.setClass(context, LineManage.class);
        else if (textView.getText().toString().equals(context.getString(R.string.product_inspect)))
            intent.setClass(context, Zcj.class);
        else if (textView.getText().toString().equals(context.getString(R.string.supplier_pallet)))
            intent.setClass(context, CombinPalletSupplier.class);
        else if (textView.getText().toString().equals(context.getString(R.string.third_move_warehouse))) {
            intent.setClass(context, InnerMoveScan.class);
            intent.putExtra("FunctionType", 1);
        }
        if (intent != null)
            startActivityLeft(intent);
    }


    @Override
    protected void initData() {
        super.initData();

    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        ArrayList<Integer> itemIconList = new ArrayList<>();
        ArrayList<String> itemNamesList = new ArrayList<>();
        List<MenuInfo> menuInfos = BaseApplication.userInfo.getLstMenu();
        if (menuInfos != null) {
            for (int i = 0; i < menuInfos.size(); i++) {
                String nodUrl = menuInfos.get(i).getNodeUrl();
                if (!CommonUtil.isNumeric(nodUrl)) continue;
                int Node = Integer.parseInt(nodUrl);
                switch (Node) {
                    case 1:
                        itemIconList.add(R.drawable.qc);
                        itemNamesList.add(context.getString(R.string.QC_title));
                        break;
                    case 2:
                        itemIconList.add(R.drawable.receiption);
                        itemNamesList.add(context.getString(R.string.receipt_title));
                        break;
                    case 3:
                        itemIconList.add(R.drawable.upshelves);
                        itemNamesList.add(context.getString(R.string.UpShelf_title));
                        break;
                    case 4:
                        itemIconList.add(R.drawable.offshelf);
                        itemNamesList.add(context.getString(R.string.OffShelf_title));
                        break;
                    case 5:
                        itemIconList.add(R.drawable.review);
                        itemNamesList.add(context.getString(R.string.Review_title));
                        break;
                    case 6:
                        itemIconList.add(R.drawable.innermove);
                        itemNamesList.add(context.getString(R.string.InnerMove_title));
                        break;
                    case 7:
                        itemIconList.add(R.drawable.inventory);
                        itemNamesList.add(context.getString(R.string.Intentory_title));
                        itemIconList.add(R.drawable.intentoryfinc);
                        itemNamesList.add(context.getString(R.string.financial_inventory));
                        break;
                    case 8:
                        itemIconList.add(R.drawable.query);
                        itemNamesList.add(context.getString(R.string.query_title));
                        break;
                    case 9:
                        itemIconList.add(R.drawable.combinepallet);
                        itemNamesList.add(context.getString(R.string.Pallet_title));
                        break;
                    case 10:
                        itemIconList.add(R.drawable.dismantlepallet);
                        itemNamesList.add(context.getString(R.string.DisPallet_title));
                        break;
                    case 11:
                        itemIconList.add(R.drawable.dismounting);
                        itemNamesList.add(context.getString(R.string.pack_unpack));
                        break;
                    case 12:
                        itemIconList.add(R.drawable.materiel);
                        itemNamesList.add(context.getString(R.string.material_transfer));
                        break;
                    case 13:
                        itemIconList.add(R.drawable.fillprint);
                        itemNamesList.add(context.getString(R.string.patch_print));
                        break;
                    case 14:
                        itemIconList.add(R.drawable.adjustment);
                        itemNamesList.add(context.getString(R.string.adjust_title));
                        break;
                    case 15:
                        itemIconList.add(R.drawable.receiption);
                        itemNamesList.add(context.getString(R.string.LineStockInMaterialY));
                        break;
                    case 16:
                        itemIconList.add(R.drawable.returnmaterial);
                        itemNamesList.add(context.getString(R.string.LineStockInReturnBillChoice));
                        break;
                    case 17:
                        itemIconList.add(R.drawable.receiptsemiproduct);
                        itemNamesList.add(context.getString(R.string.Product_ProductStockin_subtitleYMH));
                        break;
                    case 18:
                        itemIconList.add(R.drawable.packagematerial);
                        itemNamesList.add(context.getString(R.string.LineStockOutMaterial));
                        break;
                    case 19:
                        itemIconList.add(R.drawable.semiproduct);
                        itemNamesList.add(context.getString(R.string.LineStockOutReturnBillChoice));
                        break;
                    case 20:
                        itemIconList.add(R.drawable.deliveryproduct);
                        itemNamesList.add(context.getString(R.string.Product_ProductStockout_subtitleYMH));
                        break;
                    case 21:
                        itemIconList.add(R.drawable.productmanage);
                        itemNamesList.add(context.getString(R.string.Product_manage_subtitle));
                        break;
                    case 22:
                        itemIconList.add(R.drawable.receiptproduct);
                        itemNamesList.add(context.getString(R.string.Product_ProductYMH));
                        break;
                    case 25:
                        itemIconList.add(R.drawable.adjustment);
                        itemNamesList.add(context.getString(R.string.Product_Product_adjustCPYMH));
                        break;
                    case 26:
                        itemIconList.add(R.drawable.qc);
                        itemNamesList.add(context.getString(R.string.product_inspect));
                        break;
                    case 27:
                        itemIconList.add(R.drawable.combinepallet);
                        itemNamesList.add(context.getString(R.string.supplier_pallet));
                        break;
                    case 33:
                        itemIconList.add(R.drawable.innermove);
                        itemNamesList.add(context.getString(R.string.third_move_warehouse));
                        break;
                }
            }

            //cion和iconName的长度是相同的，这里任选其一都可以
            for (int i = 0; i < itemIconList.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("image", itemIconList.get(i));
                map.put("text", itemNamesList.get(i));
                data_list.add(map);
            }
        }
//        data_list = addTestMenu(itemNamesList, itemIconList);
        return data_list;
    }

    public List<Map<String, Object>> addTestMenu(@NonNull ArrayList<String> itemNamesList, ArrayList<Integer> itemIconList) {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        itemIconList.add(R.drawable.qc);
        itemNamesList.add(context.getString(R.string.QC_title));

        itemIconList.add(R.drawable.receiption);
        itemNamesList.add(context.getString(R.string.receipt_title));

        itemIconList.add(R.drawable.upshelves);
        itemNamesList.add(context.getString(R.string.UpShelf_title));

        itemIconList.add(R.drawable.offshelf);
        itemNamesList.add(context.getString(R.string.OffShelf_title));

        itemIconList.add(R.drawable.review);
        itemNamesList.add(context.getString(R.string.Review_title));

        itemIconList.add(R.drawable.innermove);
        itemNamesList.add(context.getString(R.string.InnerMove_title));

        itemIconList.add(R.drawable.inventory);
        itemNamesList.add(context.getString(R.string.Intentory_title));
        itemIconList.add(R.drawable.intentoryfinc);
        itemNamesList.add(context.getString(R.string.financial_inventory));

        itemIconList.add(R.drawable.query);
        itemNamesList.add(context.getString(R.string.query_title));

        itemIconList.add(R.drawable.combinepallet);
        itemNamesList.add(context.getString(R.string.Pallet_title));

        itemIconList.add(R.drawable.dismantlepallet);
        itemNamesList.add(context.getString(R.string.DisPallet_title));

        itemIconList.add(R.drawable.dismounting);
        itemNamesList.add(context.getString(R.string.pack_unpack));

        itemIconList.add(R.drawable.materiel);
        itemNamesList.add(context.getString(R.string.material_transfer));

        itemIconList.add(R.drawable.fillprint);
        itemNamesList.add(context.getString(R.string.patch_print));

        itemIconList.add(R.drawable.adjustment);
        itemNamesList.add(context.getString(R.string.adjust_title));

        itemIconList.add(R.drawable.receiption);
        itemNamesList.add(context.getString(R.string.LineStockInMaterialY));

        itemIconList.add(R.drawable.returnmaterial);
        itemNamesList.add(context.getString(R.string.LineStockInReturnBillChoice));

        itemIconList.add(R.drawable.receiptsemiproduct);
        itemNamesList.add(context.getString(R.string.Product_ProductStockin_subtitleYMH));

        itemIconList.add(R.drawable.packagematerial);
        itemNamesList.add(context.getString(R.string.LineStockOutMaterial));

        itemIconList.add(R.drawable.semiproduct);
        itemNamesList.add(context.getString(R.string.LineStockOutReturnBillChoice));

        itemIconList.add(R.drawable.deliveryproduct);
        itemNamesList.add(context.getString(R.string.Product_ProductStockout_subtitleYMH));

        itemIconList.add(R.drawable.productmanage);
        itemNamesList.add(context.getString(R.string.Product_manage_subtitle));

        itemIconList.add(R.drawable.receiptproduct);
        itemNamesList.add(context.getString(R.string.Product_ProductYMH));

        itemIconList.add(R.drawable.adjustment);
        itemNamesList.add(context.getString(R.string.Product_Product_adjustCPYMH));

        itemIconList.add(R.drawable.qc);
        itemNamesList.add(context.getString(R.string.product_inspect));

        itemIconList.add(R.drawable.combinepallet);
        itemNamesList.add(context.getString(R.string.supplier_pallet));

        itemIconList.add(R.drawable.innermove);
        itemNamesList.add(context.getString(R.string.third_move_warehouse));
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < itemIconList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", itemIconList.get(i));
            map.put("text", itemNamesList.get(i));
            data_list.add(map);
        }
        return data_list;
    }
}
