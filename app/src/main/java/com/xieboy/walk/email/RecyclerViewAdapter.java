package com.xieboy.walk.email;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * Created by Walk on 2017/4/29.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Map<String, Object>> list;
    private NotesDB DB;
    public static final int LONG_CLICK = 1;
    private SQLiteDatabase dbread;
    private Action action;
    private ViewOnLongClickListener listener = new ViewOnLongClickListener() {
        @Override
        public void onLongClick(View v, int position) {
            final int n = position;
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
            builder.setTitle("删除该邮件");
            builder.setMessage("删掉就没有了哦，真的要删吗？");
            builder.setIcon(R.drawable.ic_alert_grey600_48dp);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String content = list.get(n) + "";
                    String content1 = content.substring(content.indexOf("=") + 1,
                            content.indexOf(","));
                    Log.d("CONTENT", content1);
                    Log.d(TAG, content);
                    Log.d(TAG, content.indexOf("=") + 1 + "");
                    Log.d(TAG, content.indexOf(",") + "");
                    Cursor c = dbread.query("note", null, "title=" + "'"
                            + content1 + "'", null, null, null, null);
                    while (c.moveToNext()) {
                        String id = c.getString(c.getColumnIndex("_id"));
                        String sql_del = "delete from note  where _id="
                                + id;
                        Log.d("CONTENT1", id);
                        dbread.execSQL(sql_del);

                    }
                    c.close();
                    notifyDataSetChanged();
                    publish(LONG_CLICK);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create();
            if (n!=0)
            builder.show();
        }
    };
    private ViewOnclickListener listenerClick = new ViewOnclickListener() {
        @Override
        public void onClick(View v, final int position) {

            publish(2);
            ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationZ", 20, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Intent intent=new Intent(mContext,activity_detail.class);
                    Map<String, Object> m = list.get(position);
                    String content;
                    if (position==0){
                        content="\n汇聚Ymail之精华，随时随地供你使用\n安装官方的Ymail应用，你才能使用Ymail的最佳功能，或者访问Ymail.xieboy.cn即可开始使用，祝你使用愉快。\nYmail敬上";

                    }else
                    content = m.get("cv_content").toString().trim();
                    String title = m.get("cv_title").toString().trim();
                    String reciever = m.get("cv_reciever").toString().trim();
                    String date = m.get("cv_date").toString().trim();

                   intent.putExtra("content",content);
                   intent.putExtra("title",title);
                   intent.putExtra("reciever",reciever);
                   intent.putExtra("date",date);
                    mContext.startActivity(intent);
                }
            });
            Log.d(TAG, "onClick: " + position);
            animator.start();

        }
    };

    private void publish(int what) {
        if (action != null) {
            action.onCall(what);
        }
    }

    public RecyclerViewAdapter setListChangedListener(Action action) {
        this.action = action;
        return this;
    }

    public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public RecyclerViewAdapter setList(List<Map<String, Object>> list) {
        this.list = list;
        notifyDataSetChanged();
        return this;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview, parent, false);
        DB = new NotesDB(mContext);
        dbread = DB.getReadableDatabase();
        return new ViewHolder(view).setListener(listener).setClickListener(listenerClick);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
        //final View view = holder.mView;
        Map<String, Object> m = list.get(position);
        String content = m.get("cv_content").toString().trim();
        String title = m.get("cv_title").toString().trim();
        String reciever = m.get("cv_reciever").toString().trim();
        String date = m.get("cv_date").toString().trim();
        if (!content.equals("")) {
            holder.cv_title.setText(title);
            holder.cv_reciever.setText(reciever);
            holder.cv_content.setText(content);
            holder.cv_date.setText(date);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public CardView cardView;
        public TextView cv_reciever;
        public TextView cv_title;
        public TextView cv_content;
        public TextView cv_date;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cv_title = (TextView) view.findViewById(R.id.cv_title);
            cv_reciever = (TextView) view.findViewById(R.id.cv_reviever);
            cv_date = (TextView) view.findViewById(R.id.cv_date);
            cv_content = (TextView) view.findViewById(R.id.cv_content);
            cardView = (CardView) view.findViewById(R.id.cardview);
        }

        public ViewHolder setListener(final ViewOnLongClickListener listener1) {
            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener1.onLongClick(v, getAdapterPosition());
                    return false;
                }
            });
            return this;
        }

        public ViewHolder setClickListener(final ViewOnclickListener listener2) {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onClick(v, getAdapterPosition());
                }
            });
            return this;
        }
    }

}
