package com.jin.androidfreeerp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ItemUpdateActivity extends AppCompatActivity {

    private ItemDao itemDao;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_update);

        AppDatabase itemDb = Room.databaseBuilder(this, AppDatabase.class, "Item-db")
                .allowMainThreadQueries()
                .build();
        itemDao = itemDb.itemDao();

        Button buttonItemSave = findViewById(R.id.buttonItemSave);
        Button buttonItemDel = findViewById(R.id.buttonItemDel);
        EditText editTextsItemCode = findViewById(R.id.editTextsItemCode);
        EditText editTextsItemName = findViewById(R.id.editTextsItemName);
        EditText editTextiPurchasePrice = findViewById(R.id.editTextiPurchasePrice);
        EditText editTextiSellingPrice = findViewById(R.id.editTextiSellingPrice);
        EditText editTextsItemMemo = findViewById(R.id.editTextsItemMemo);

        Intent intent = getIntent();
        String sItemCode = intent.getExtras().getString("itemId");
        if(sItemCode.equals("")){
            if (itemDao.countCode() > 0)
            {
                editTextsItemCode.setText(Integer.toString(Integer.parseInt(itemDao.newCode()) + 1));
            } else {

            }
        }else {
            editTextsItemCode.setText(sItemCode);
            itemList = itemDao.selectCode(editTextsItemCode.getText().toString());
            editTextsItemName.setText(itemList.get(0).getItemName());
            editTextiPurchasePrice.setText(Integer.toString(itemList.get(0).getPurchasePrice()));
            editTextiSellingPrice.setText(Integer.toString(itemList.get(0).getSellingPrice()));
            editTextsItemMemo.setText(itemList.get(0).getItemMemo());
        }

        buttonItemSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (editTextsItemCode.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextsItemName.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextiPurchasePrice.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextiSellingPrice.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextsItemMemo.getText().length() == 0) {
                    editTextsItemMemo.setText("");
                };

                if (itemDao.countCode(editTextsItemCode.getText().toString()) > 0)
                {
                    AlertDialog.Builder saveAD = new AlertDialog.Builder(ItemUpdateActivity.this);
                    saveAD.setIcon(R.mipmap.ic_launcher);
                    saveAD.setTitle("??????");
                    saveAD.setMessage("?????? ????????? ???????????? ???????????????????");
                    saveAD.setPositiveButton("???", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            itemDao.updateCode(editTextsItemCode.getText().toString()
                                    , editTextsItemName.getText().toString()
                                    , Integer.parseInt(editTextiPurchasePrice.getText().toString())
                                    , Integer.parseInt(editTextiSellingPrice.getText().toString())
                                    , editTextsItemMemo.getText().toString());
                            Toast.makeText(ItemUpdateActivity.this, "???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    saveAD.setNegativeButton("?????????.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){

                        }
                    });
                    saveAD.show();
                }
                else {
                    Item item = new Item(editTextsItemCode.getText().toString()
                            , editTextsItemName.getText().toString()
                            , Integer.parseInt(editTextiPurchasePrice.getText().toString())
                            , Integer.parseInt(editTextiSellingPrice.getText().toString())
                            , editTextsItemMemo.getText().toString()
                            , 0
                            , 0);

                    itemDao.insert(item);
                    Toast.makeText(ItemUpdateActivity.this, "?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonItemDel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                itemDao.deleteCode(editTextsItemCode.getText().toString());
                Toast.makeText(ItemUpdateActivity.this, "?????????????????????.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}