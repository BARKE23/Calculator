package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //创建Button对象
    Button button_div, button_mul, button_add, button_sub, button_rad;
    Button button_clear, button_equ, button_del;
    Button button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_dot;
    Button button_oppo;
    EditText result;
    boolean clr_flag;
    Calculator cal = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_0 = findViewById(R.id.button_0);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_4 = findViewById(R.id.button_4);
        button_5 = findViewById(R.id.button_5);
        button_6 = findViewById(R.id.button_6);
        button_7 = findViewById(R.id.button_7);
        button_8 = findViewById(R.id.button_8);
        button_9 = findViewById(R.id.button_9);
        button_dot = findViewById(R.id.button_dot);
        button_add = findViewById(R.id.button_add);
        button_sub = findViewById(R.id.button_sub);
        button_mul = findViewById(R.id.button_mul);
        button_div = findViewById(R.id.button_div);
        button_rad = findViewById(R.id.button_rad);
        button_clear = findViewById(R.id.button_clear);
        button_equ = findViewById(R.id.button_equ);
        button_del = findViewById(R.id.button_del);
        button_oppo = findViewById(R.id.button_oppo);
        result = findViewById(R.id.result);

        //给按钮设置点击事件
        button_0.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);
        button_dot.setOnClickListener(this);
        button_add.setOnClickListener(this);
        button_sub.setOnClickListener(this);
        button_mul.setOnClickListener(this);
        button_div.setOnClickListener(this);
        button_rad.setOnClickListener(this);
        button_clear.setOnClickListener(this);
        button_equ.setOnClickListener(this);
        button_del.setOnClickListener(this);
        button_oppo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(result.getText().toString().equals("ERROR") ){
            v.getId();
            result.setText("");
            return;
        }
        String str = result.getText().toString();//获取文本框中的文本内容
        switch (v.getId()) {

            case R.id.button_0:
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
                if (clr_flag) {//如果需要清空
                    clr_flag = false;
                    str = "";
                    result.setText("");
                }
                result.setText(str + ((Button) v).getText());
                break;

            //小数点处理
            case R.id.button_dot:
                int dot_flag = 0;//小数点的数量，检测一个数中不可存在多个小数点时使用
                if (str.isEmpty()) { //空白时输入小数点
                    result.setText("0.");
                    break;
                }

                if (str.charAt(str.length() - 1) == '.') //不能连续输入小数点
                    return;

                //在符号后直接输入小数点
                if (str.charAt(str.length() - 1) == '+' || str.charAt(str.length() - 1) == '-' || str.charAt(str.length() - 1) == '*' || str.charAt(str.length() - 1) == '/') {
                    result.setText(str + "0.");
                    break;
                }

                if(str.charAt(str.length() - 1) == ')'){
                    Toast.makeText(MainActivity.this, "请先输入数字，再转换符号", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = str.length() - 1; i >= 0; i--) {    //检测是否在一个数中输入多个小数点
                    if (str.charAt(i) == '.') {
                        dot_flag++;
                    }
                    if (dot_flag >= 1) {
                        return;
                    }
                    if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/') {
                        break;
                    }
                }
                if (dot_flag <= 1) {
                    result.setText(str + ((Button) v).getText());
                }
                break;


            //运算符 + - × ÷
            case R.id.button_add:
            case R.id.button_sub:
            case R.id.button_mul:
            case R.id.button_div:
                if (clr_flag) {
                    clr_flag = false;
                    str = "";
                    result.setText("");
                }

                if (!str.isEmpty()) {    //不空时才能输入运算符
                    if (str.charAt(str.length() - 1) == '+' || str.charAt(str.length() - 1) == '-' || str.charAt(str.length() - 1) == '*' || str.charAt(str.length() - 1) == '/') {    //用户在连续输入运算符
                        str = str.substring(0, str.length() - 1);
                    }
                    result.setText(str + ((Button) v).getText());
                } else
                    return;
                break;


            //del删除键
            case R.id.button_del:
                if (clr_flag)
                    clr_flag = false;
                if (str.isEmpty()) {
                    return;
                }
                if(str.charAt(str.length() - 1) == ')') {//碰到带括号就把（）中内容全部delete
                    for (int i = str.length() - 1; i >= 0; i--) {
                        for (int j = i; j >= 0; j--) {
                            if (str.charAt(j) == '(') {
                                result.setText(str.substring(0, j));
                                return;
                            }
                        }
                    }
                }
                str = str.substring(0, str.length() - 1);
                result.setText(str);
                break;

            //clear按键
            case R.id.button_clear:
                if (clr_flag) {
                    clr_flag = false;
                    str = "";
                    result.setText("");
                }
                str = "";
                result.setText(str);
                break;

            //+/-运算符
            case R.id.button_oppo:
                if(str.isEmpty()) {
                    return;
                }
                //以符号结尾说明未输入一个可转换的数据
                if(str.charAt(str.length() - 1) == '+' || str.charAt(str.length() - 1) == '-' || str.charAt(str.length() - 1) == '*' || str.charAt(str.length() - 1) == '/' || str.charAt(str.length() - 1) == '.') {
                    Toast.makeText(MainActivity.this, "请先输入数字，再转换符号", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i = str.length() - 1; i >= 0 ; i--) {
                    if (str.charAt(i) == ')') {     //如果当前数字就是负数
                        for (int j = i; j >= 0; j--) {
                            if (str.charAt(j) == '(') {
                                result.setText(str.substring(0, j) + str.substring(j + 3, i));
                                break;
                            }
                        }
                        break;
                    }
                    //遇到运算符或字符串扫描结束即转换当前数字
                    if(str.charAt(i) == '*' || str.charAt(i) == '/'){
                        result.setText(str.substring(0, i + 1) + "(0-" + str.substring(i + 1 , str.length()) + ")");
                        break;
                    }
                    if(str.charAt(i) == '-' && i != 0) {
                        result.setText(str.substring(0, i) + "+" + str.substring(i + 1, str.length()));
                        break;
                    }
                    if(str.charAt(i) == '+' && i != 0) {
                        result.setText(str.substring(0, i + 1) + "(0-" + str.substring(i + 1, str.length()) + ")");
                        break;
                    }
                    if(i == 0){
                        if(str.charAt(0) == '-')
                            result.setText(str.substring(1, str.length()));
                        else
                            result.setText("(0-" + str + ")");
                        break;
                    }
                }
                break;

            //√运算符
            case R.id.button_rad:
                if(str.isEmpty()){
                    return;
                }
                if(str.charAt(str.length() - 1) == '+' || str.charAt(str.length() - 1) == '-' || str.charAt(str.length() - 1) == '*' || str.charAt(str.length() - 1) == '/' || str.charAt(str.length() - 1) == '.'){
                    Toast.makeText(MainActivity.this, "输入有误！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(str.charAt(0) == '-') {
                    str = "0" + str;
                }
                if(Double.parseDouble(cal.calculate(str)) >= 0)
                    result.setText("" + Math.sqrt(Double.parseDouble(cal.calculate(str))));
                else {
                    Toast.makeText(MainActivity.this, "不能计算负数的平方根！", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;

            //等于按键
            case R.id.button_equ: //单独运算最后结果
                if(str.isEmpty()){
                    return;
                }

                if(str.charAt(0) == '-') {
                    str = "0" + str;
                }
                if (str.length() == 0 || str.charAt(str.length() - 1) == '+' || str.charAt(str.length() - 1) == '-' || str.charAt(str.length() - 1) == '*' || str.charAt(str.length() - 1) == '/' || str.charAt(str.length() - 1) == '.' || str.charAt(str.length() - 1) == '√') {
                    Toast.makeText(MainActivity.this, "输入有误！", Toast.LENGTH_SHORT).show();
                    break;
                }
                result.setText(cal.calculate(str));
                break;
        }
    }
}