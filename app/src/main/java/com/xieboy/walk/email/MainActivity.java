package com.xieboy.walk.email;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private Context mContext;
    private NoscollListView listview;
    private FloatingActionButton addNote;
    DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_main);
        getWindow().setTitle("");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView =
                (NavigationView) findViewById(R.id.nv_main_navigation);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        //tv_content = (TextView) findViewById(R.id.tv_content);
        listview = (NoscollListView)findViewById(R.id.listview);
        //dataList = new ArrayList<Map<String,Object>>();
        //dataList1 = new ArrayList<Map<String,Object>>();
        addNote = (FloatingActionButton) findViewById(R.id.btn_editnote);
        mContext= this;
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            noteEdit.ENTER_STATE=0;
                Intent intent=new Intent(mContext,noteEdit.class);
                Bundle bundle=new Bundle();
                bundle.putString("info","");
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });



        // 清空数据库中表的内容
        //dbread.execSQL("delete from note");

//        listview.setOnItemClickListener(this);
//        listview.setOnItemLongClickListener(this);
//        listview.setOnScrollListener(this);


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<>();
        titles.add("欢迎使用Ymail");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        List<Fragment> fragments = new ArrayList<>();

        //传输数据
//        dataList1=getData();
        listfragment listfragments=new listfragment();
//        Bundle bundle1=new Bundle();
//        ArrayList bundlelist = new ArrayList();
//        bundlelist.add(dataList1);
//        bundle1.putParcelableArrayList("list",bundlelist);
//        listfragments.setArguments(bundle1);

        fragments.add(listfragments);
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        setTitle(menuItem.getTitle()); // 改变页面标题，标明导航状态
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()){
                            case R.id.nav_about:
                                android.support.v7.app.AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                                builder.setMessage(R.string.about_content);
                                builder.setTitle(menuItem.getTitle());
                                builder.setNegativeButton("确定",null);
                                builder.setIcon(R.drawable.ic_alert_grey600_48dp);
                                setTitle("Ymail");
                                builder.show();
                            case R.id.nav_important:;
                            case R.id.nav_sended:;
                            case R.id.nav_all_mails:
                            case R.id.nav_drafts:;
                            case R.id.nav_trash:;
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//    switch (scrollState){
//        case SCROLL_STATE_FLING:
//            Log.i("main","用户在手指离开屏幕之前，由于用力的滑了一下，视图能依靠惯性继续滑动");
//        case SCROLL_STATE_IDLE:
//            Log.i("main", "视图已经停止滑动");
//        case SCROLL_STATE_TOUCH_SCROLL:
//            Log.i("main", "手指没有离开屏幕，试图正在滑动");
//    }
//    }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        noteEdit.ENTER_STATE = 1;
//
//        String content = listview.getItemAtPosition(position) + "";
//        String content1 = content.substring(content.indexOf("=") + 1,
//                content.indexOf(","));
//        Log.d("CONTENT", content1);
//        Cursor c = dbread.query("note", null,
//                "content=" + "'" + content1 + "'", null, null, null, null);
//        while (c.moveToNext()) {
//            String No = c.getString(c.getColumnIndex("_id"));
//            Log.d("TEXT", No);
//            // Intent intent = new Intent(mContext, noteEdit.class);
//            // intent.putExtra("data", text);
//            // setResult(4, intent);
//            // // intent.putExtra("data",text);
//            // startActivityForResult(intent, 3);
//
////启动noteedit
//            Intent myIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("info", content1);
//            noteEdit.id = Integer.parseInt(No);
//            myIntent.putExtras(bundle);
//            myIntent.setClass(MainActivity.this, noteEdit.class);
//            startActivityForResult(myIntent, 1);
//        }
//
//    }
//长按删除
//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//        final int n=position;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("删除该日志");
//        builder.setMessage("确认删除吗？");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String content = listview.getItemAtPosition(n) + "";
//                String content1 = content.substring(content.indexOf("=") + 1,
//                        content.indexOf(","));
//                Cursor c = dbread.query("note", null, "content=" + "'"
//                        + content1 + "'", null, null, null, null);
//                while (c.moveToNext()) {
//                    String id = c.getString(c.getColumnIndex("_id"));
//                    String sql_del = "update note set content='' where _id="
//                            + id;
//                    dbread.execSQL(sql_del);
//                    RefreshNotesList();
//                }
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        builder.create();
//        builder.show();
//        return true;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
        }
    }
}
