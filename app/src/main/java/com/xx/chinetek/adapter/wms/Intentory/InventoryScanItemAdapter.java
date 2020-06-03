package com.xx.chinetek.adapter.wms.Intentory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xx.chinetek.cywms.R;
import com.xx.chinetek.model.WMS.Inventory.Barcode_Model;
import com.xx.chinetek.util.function.ArithUtil;

import java.util.ArrayList;


/**
 * Created by GHOST on 2017/1/13.
 */

public class InventoryScanItemAdapter extends BaseAdapter {
    private Context context; // 运行上下文
    private ArrayList<Barcode_Model> barcodeModels; // 信息集合
    private LayoutInflater listContainer; // 视图容器
    int model=-1;

    public final class ListItemView { // 自定义控件集合

        public TextView txtMaterialNo;
        public TextView txtStock;
        public TextView txtQty;
        public TextView txtMaterialDec;
        public TextView txtScanQty;
    }

    public InventoryScanItemAdapter(Context context,int model, ArrayList<Barcode_Model> barcodeModels) {
        this.context = context;
        listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
        this.barcodeModels = barcodeModels;
        this.model=model;

    }

    @Override
    public int getCount() {
        return barcodeModels==null?0:barcodeModels.size();
    }

    @Override
    public Object getItem(int position) {
        return barcodeModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int selectID = position;
        // 自定义视图
        ListItemView listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();

            // 获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.item_inventoryscan_listview,null);
            listItemView.txtMaterialNo = (TextView) convertView.findViewById(R.id.txtMaterialNo);
            listItemView.txtQty = (TextView) convertView.findViewById(R.id.txtQty);
            listItemView.txtStock = (TextView) convertView.findViewById(R.id.txtStock);
            listItemView.txtMaterialDec = (TextView) convertView.findViewById(R.id.txtMaterialDec);
            listItemView.txtScanQty = (TextView) convertView.findViewById(R.id.txtScanQty);
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
        Barcode_Model barcodeModel=barcodeModels.get(selectID);
        listItemView.txtMaterialNo.setText(barcodeModel.getMaterialNo());
        listItemView.txtStock.setText(context.getString(R.string.stock_location)+barcodeModel.getAreano());
        listItemView.txtMaterialDec.setText(barcodeModel.getMaterialDesc());
        listItemView.txtQty.setText(context.getString(R.string.Count)+barcodeModel.getQty());
        if(model==1){
            listItemView.txtScanQty.setVisibility(View.GONE);
        }else{
            listItemView.txtScanQty.setText(context.getString(R.string.scan_qty)+barcodeModel.getSQTY());
        }
        if(model==2){
            Float remainQty= ArithUtil.sub(barcodeModel.getQty(),barcodeModel.getSQTY());
            if(remainQty>0f)
                convertView.setBackgroundResource(R.color.lightgreen);
            else if(remainQty==0f)
                convertView.setBackgroundResource(R.color.springgreen);
            else if(remainQty<0f)
                convertView.setBackgroundResource(R.color.firebrick);
            if(barcodeModel.getSQTY()==null || (barcodeModel.getSQTY()!=null && barcodeModel.getSQTY()==0f)){
                convertView.setBackgroundResource(R.color.trans);
            }

        }

        return convertView;
    }


}
