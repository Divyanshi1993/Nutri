package Com.knoventive.nutri.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import java.io.InputStream;
import java.util.ArrayList;

import Com.knoventive.nutri.Adapter.NutritionListAdapter;
import Com.knoventive.nutri.Util.CustomTextView;
import Com.knoventive.nutri.Util.Email_info;
import Com.knoventive.nutri.Util.ScreenOrientationControl;
import Com.knoventive.nutri.R;
import Com.knoventive.nutri.model.DataList;


public class RegimenScreen extends AppCompatActivity implements View.OnClickListener {
    public NutritionListAdapter nutListAdapter;
    private TextView email_reg, recommended_product;
    private AssetManager assetManager;
    private ArrayList<DataList> nutritionList;
    private InputStream mInput_Calorielist, mInput_mainlist;
    private String cell_main_desc_str, cell_main_data_str, cell_cal_str;
    private int  _calorie, _weight;
    private Cell cell_cal, cell_main_data, cell_main_desc;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    public static Context reg_Context;
    private CustomTextView toolbar_label2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ScreenOrientationControl(this).setOrientation();
        setContentView(R.layout.activity_regimen__screen);
        initViews();
        setListners();
        assetManager = getAssets();
        nutritionList = new ArrayList<DataList>();
        getIntentData();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reg_Context = this;
        toolbar_label2.setText(_weight + " kgs | " + _calorie + " kcal/kg BW/d");
        //  XX kgs | XX kcal/kg BW/d
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    get data from intent and Calculate Weight value
     */
    private void getIntentData() {
        int column = 0, weight_value, main_data_coul_num = 0;
        float divideBy = 5;
        Intent intent = getIntent();
        if (intent.hasExtra(getResources().getString(R.string.cal)) && intent.hasExtra(getString(R.string.Weight))) {
            _calorie = intent.getIntExtra(getString(R.string.cal),0);
            _weight = intent.getIntExtra(getString(R.string.Weight),0);

/*            Round off weight :
               First  Convert _weight to float and divide it by float to get a float value,than round off
               the float value  to integer ,Mutiply converted value with 5 in order to get round of weight value
 */


            weight_value = Integer.valueOf(Math.round(Float.valueOf(_weight) / divideBy)) * 5;

            switch (weight_value) {
                case 50:
                case 75:
                    column = (_calorie -15)+0;
                    break;
                case 55:
                case 80:
                    column = (_calorie -15)+1;
                    break;
                case 60:
                case 85:
                    column = (_calorie -15)+2;
                    break;
                case 65:
                case 90:
                    column = (_calorie -15)+3;
                    break;
                case 70:
                case 95:
                    column = (_calorie -15)+4;
                    break;
            }

            switch (_calorie){
                case  15:
                    main_data_coul_num = 1;
                    break;
                case  20:
                    main_data_coul_num = 2;
                    break;
                case  25:
                    main_data_coul_num = 3;
                    break;
                case  30:
                    main_data_coul_num = 4;
                    break;
                case  35:
                    main_data_coul_num = 5;
                    break;
            }
            readExcelFileFromAssets(column, weight_value, main_data_coul_num);
        }
    }

    /*Initialize views here
            */
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        email_reg = findViewById(R.id.email_reg);
        recommended_product = findViewById(R.id.recom_prod);
        recyclerView = findViewById(R.id.nutrition_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        toolbar_label2 = findViewById(R.id.label2_toolbar);

    }

    /*
           Set on click of views
            */
    private void setListners() {
        email_reg.setOnClickListener(this);
        recommended_product.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_reg:
                new Email_info(this, nutritionList);

                break;
            case R.id.recom_prod:
                navigateToPdfScreen("");
                break;
        }
    }

    public  void navigateToPdfScreen(String desc) {
        Intent product_screen = new Intent(this, Product_info_pdf_screen.class);
        if(desc.equals("986")){
        product_screen.putExtra("filename", "Recommended Product Sheets - 1000ml.pdf");}
       else if(desc.equals("1477")){
            product_screen.putExtra("filename", "Recommended Product Sheets - 1500ml.pdf");}
        else if(desc.equals("1970")){
            product_screen.putExtra("filename", "Recommended Product Sheets - 2000ml.pdf");}
        else if(desc.equals("2463")){
            product_screen.putExtra("filename", "Recommended Product Sheets - 2500ml.pdf");}
        product_screen.putExtra("header_text", "Recommended Product");
        startActivity(product_screen);
    }

    /*
    read Excel file here
     */
    public void readExcelFileFromAssets(int column, int weight, int main_data_coul_num) {
        try {
            // Creating Input Stream
            nutritionList.clear();
            if (weight >= 50 && weight < 75) {
                mInput_Calorielist = assetManager.open("smof_print_50_70.xls");
            } else if (weight >= 75 && weight <= 95) {
                mInput_Calorielist = assetManager.open("smof_print_75_95.xls");
            }
            mInput_mainlist = assetManager.open("smof_print_50_70_main_sheet.xls");
            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem_cal = new POIFSFileSystem(mInput_Calorielist);
            POIFSFileSystem myFileSystem_main = new POIFSFileSystem(mInput_mainlist);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook_cal = new HSSFWorkbook(myFileSystem_cal);
            HSSFWorkbook myWorkBook_main = new HSSFWorkbook(myFileSystem_main);
            // Get the first sheet from workbook
            HSSFSheet mySheet_cal = myWorkBook_cal.getSheetAt(0);
            HSSFSheet mySheet_main = myWorkBook_main.getSheetAt(0);

            for (int rowIndex = 0; rowIndex <= mySheet_cal.getLastRowNum(); rowIndex++) {
                HSSFRow row_cal = mySheet_cal.getRow(rowIndex);
                HSSFRow row_main = mySheet_main.getRow(rowIndex);
                if (row_cal != null && row_main != null) {

                    cell_cal = row_cal.getCell(column);
                    cell_main_data = row_main.getCell(main_data_coul_num);
                    cell_main_desc = row_main.getCell(0);

                    cell_main_desc_str = cell_main_desc.toString();

                    //
                    if(cell_main_desc_str.contains("Infusion time (h)")){
                        String part1=   cell_main_desc_str.substring(0,17);
                        String part2=   cell_main_desc_str.substring(17);
                        cell_main_desc_str =part1+"\n"+part2;
                    }
                    cell_cal_str = cell_cal.toString();
                    if (cell_cal_str.equals("na")) {
                        cell_cal_str = " ";
                    }

                    cell_main_data_str = cell_main_data.toString();
                    if (cell_main_data_str.equals("na")) {
                        cell_main_data_str = " ";
                    } else {
                        cell_main_data_str = "≥" + cell_main_data_str;
                    }

                    nutritionList.add(new DataList(cell_main_desc_str, cell_cal_str, cell_main_data_str));
                }
            }
            for(int i =0 ;i<nutritionList.size();i++){
                if(nutritionList.get(i).getDesc().contains("493")){
                    nutritionList.remove(i);
                }

            }
            nutListAdapter = new NutritionListAdapter(this, nutritionList);
            recyclerView.setAdapter(nutListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }
}
