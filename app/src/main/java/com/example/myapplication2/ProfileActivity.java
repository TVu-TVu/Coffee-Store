package com.example.myapplication2; // Thay bằng package name thực tế của bạn

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private UserManager userManager;

    // Khai báo các TextView để hiển thị giá trị
    private TextView tvFullNameValue;
    private TextView tvPhoneNumberValue;
    private TextView tvEmailValue;
    private TextView tvAddressValue;

    // Khai báo các ImageButton để chỉnh sửa
    private ImageButton btnEditFullName;
    private ImageButton btnEditPhoneNumber;
    private ImageButton btnEditEmail;
    private ImageButton btnEditAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userManager = new UserManager(this); // Khởi tạo UserManager

        // Ánh xạ các TextView từ layout
        tvFullNameValue = findViewById(R.id.tv_full_name_value);
        tvPhoneNumberValue = findViewById(R.id.tv_phone_number_value);
        tvEmailValue = findViewById(R.id.tv_email_value);
        tvAddressValue = findViewById(R.id.tv_address_value);

        // Ánh xạ các ImageButton từ layout
        btnEditFullName = findViewById(R.id.btn_edit_full_name);
        btnEditPhoneNumber = findViewById(R.id.btn_edit_phone_number);
        btnEditEmail = findViewById(R.id.btn_edit_email);
        btnEditAddress = findViewById(R.id.btn_edit_address);

        // Tải và hiển thị thông tin người dùng khi Activity được tạo
        loadUserProfileData();

        // Thiết lập OnClickListener cho các nút chỉnh sửa
        btnEditFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(
                        tvFullNameValue,
                        getString(R.string.label_full_name),
                        userManager.getFullName(),
                        InputType.TYPE_CLASS_TEXT,
                        new OnSaveListener() {
                            @Override
                            public void onSave(String newValue) {
                                userManager.updateFullName(newValue);
                                tvFullNameValue.setText(newValue);
                            }
                        }
                );
            }
        });

        btnEditPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(
                        tvPhoneNumberValue,
                        getString(R.string.label_phone_number),
                        userManager.getPhoneNumber(),
                        InputType.TYPE_CLASS_PHONE,
                        new OnSaveListener() {
                            @Override
                            public void onSave(String newValue) {
                                userManager.updatePhoneNumber(newValue);
                                tvPhoneNumberValue.setText(newValue);
                            }
                        }
                );
            }
        });

        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(
                        tvEmailValue,
                        getString(R.string.label_email),
                        userManager.getEmail(),
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                        new OnSaveListener() {
                            @Override
                            public void onSave(String newValue) {
                                userManager.updateEmail(newValue);
                                tvEmailValue.setText(newValue);
                            }
                        }
                );
            }
        });

        btnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(
                        tvAddressValue,
                        getString(R.string.label_address),
                        userManager.getAddress(),
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE, // Cho phép nhiều dòng
                        new OnSaveListener() {
                            @Override
                            public void onSave(String newValue) {
                                userManager.updateAddress(newValue);
                                tvAddressValue.setText(newValue);
                            }
                        }
                );
            }
        });
    }

    // Hàm để tải thông tin người dùng từ SharedPreferences và hiển thị lên UI
    private void loadUserProfileData() {
        tvFullNameValue.setText(userManager.getFullName());
        tvPhoneNumberValue.setText(userManager.getPhoneNumber());
        tvEmailValue.setText(userManager.getEmail());
        tvAddressValue.setText(userManager.getAddress());
    }

    // Interface callback để xử lý lưu dữ liệu sau khi chỉnh sửa
    private interface OnSaveListener {
        void onSave(String newValue);
    }

    // Hàm chung để hiển thị AlertDialog cho phép chỉnh sửa
    private void showEditDialog(
            final TextView targetTextView,
            String fieldLabel,
            String currentValue,
            int inputType,
            final OnSaveListener onSaveListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(String.format(getString(R.string.dialog_title_edit), fieldLabel.toLowerCase())); // "Edit full name"

        // Thiết lập EditText trong AlertDialog
        final EditText input = new EditText(this);
        input.setText(currentValue);
        input.setHint(String.format(getString(R.string.dialog_hint_full_name), fieldLabel)); // Sử dụng hint phù hợp
        input.setInputType(inputType);
        if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE)) {
            input.setSingleLine(false); // Cho phép nhiều dòng cho địa chỉ
            input.setLines(3); // Số dòng gợi ý ban đầu
            input.setMaxLines(5); // Số dòng tối đa
            input.setVerticalScrollBarEnabled(true);
        }

        builder.setView(input);

        // Thiết lập các nút Save và Cancel
        builder.setPositiveButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (onSaveListener != null) {
                    onSaveListener.onSave(newValue);
                }
            }
        });
        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    // Khi người dùng quay lại màn hình Profile sau khi đã chỉnh sửa từ một Activity khác (ít khả năng xảy ra)
    // Hoặc nếu bạn muốn đảm bảo dữ liệu luôn được cập nhật khi quay lại Profile Activity
    @Override
    protected void onResume() {
        super.onResume();
        loadUserProfileData(); // Tải lại dữ liệu mỗi khi Activity resume
    }


}

