package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.*;
import java.util.Locale;

public class MainActivity extends Activity {
    TextView display; String current="0", op=""; double stored=0; boolean reset=false;
    public void onCreate(Bundle b){super.onCreate(b); build();}
    void build(){
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(16,20,16,16); root.setBackgroundColor(Color.rgb(16,17,20));
        display=new TextView(this); display.setText("0"); display.setTextColor(Color.WHITE); display.setTextSize(48); display.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL); display.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        root.addView(display,new LinearLayout.LayoutParams(-1,0,1.2f));
        String[][] keys={{"AC","⌫","%","÷"},{"7","8","9","×"},{"4","5","6","−"},{"1","2","3","+"},{"+/−","0",".","="}};
        for(String[] row:keys){LinearLayout line=new LinearLayout(this); for(String k:row){Button v=new Button(this); v.setText(k); v.setTextSize(21); v.setTextColor(Color.WHITE); v.setAllCaps(false); v.setBackgroundColor(k.equals("=")?Color.rgb(124,92,252):(isOp(k)?Color.rgb(45,47,55):Color.rgb(29,31,36))); v.setOnClickListener(x->press(((Button)x).getText().toString())); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,0,1);p.setMargins(4,4,4,4);line.addView(v,p);} root.addView(line,new LinearLayout.LayoutParams(-1,0,1));}
        setContentView(root);
    }
    boolean isOp(String s){return s.equals("÷")||s.equals("×")||s.equals("−")||s.equals("+")||s.equals("%");}
    void press(String s){
        if("0123456789".contains(s)){if(reset||current.equals("0"))current=s;else current+=s;reset=false;}
        else if(s.equals(".")){if(reset){current="0.";reset=false;}else if(!current.contains("."))current+=".";}
        else if(s.equals("AC")){current="0";stored=0;op="";reset=false;}
        else if(s.equals("⌫")){if(!reset&&current.length()>1)current=current.substring(0,current.length()-1);else current="0";}
        else if(s.equals("+/−")){if(!current.equals("0"))current=current.startsWith("-")?current.substring(1):"-"+current;}
        else if(s.equals("%")){try{current=fmt(Double.parseDouble(current)/100);}catch(Exception e){current="Errore";}}
        else if(isOp(s)){if(!op.isEmpty()&&!reset)calc();stored=Double.parseDouble(current);op=s;reset=true;}
        else if(s.equals("=")){if(!op.isEmpty()){calc();op="";}reset=true;}
        display.setText(current);
    }
    void calc(){double b=Double.parseDouble(current),r=stored;switch(op){case "+":r=stored+b;break;case "−":r=stored-b;break;case "×":r=stored*b;break;case "÷":r=b==0?Double.NaN:stored/b;break;}current=Double.isNaN(r)||Double.isInfinite(r)?"Errore":fmt(r);}
    String fmt(double d){if(Math.rint(d)==d)return String.format(Locale.US,"%.0f",d);return String.format(Locale.US,"%.10f",d).replaceAll("0+$","").replaceAll("\\.$","");}
}
