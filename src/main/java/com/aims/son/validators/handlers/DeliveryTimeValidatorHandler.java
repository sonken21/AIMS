package com.aims.son.validators.handlers;

import com.aims.son.validators.BaseFormValidator;
import com.aims.son.validators.ValidatorResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DeliveryTimeValidatorHandler extends BaseFormValidator {
    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        // Lấy giá trị trường "deliveryTime" từ form
        String deliveryTime = form.get("deliveryTime");

        // Kiểm tra null hoặc rỗng
        if (deliveryTime == null || deliveryTime.trim().isEmpty()) {
            System.out.println("Delivery time cannot be empty.");
            return ValidatorResult.error("Invalid time");
        }

        // Định dạng thời gian
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setLenient(false);

        try {
            // Lấy giờ hiện tại
            Date currentTime = new Date();

            // Phân tách giá trị deliveryTime (dạng HH:mm-HH:mm)
            String[] timeRange = deliveryTime.split("-");
            if (timeRange.length != 2) {
                System.out.println("Invalid delivery time format: " + deliveryTime);
                return ValidatorResult.error("");
            }

            // Lấy thời gian bắt đầu
            Date deliveryStartTime = timeFormat.parse(timeRange[0]);

            // Tạo đối tượng Calendar để đồng bộ hóa ngày
            Calendar currentCalendar = Calendar.getInstance();
            Calendar deliveryCalendar = Calendar.getInstance();

            deliveryCalendar.setTime(deliveryStartTime);
            deliveryCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            deliveryCalendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            deliveryCalendar.set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH));

            // So sánh thời gian bắt đầu với thời gian hiện tại
            if (deliveryCalendar.getTime().before(currentTime)) {
                System.out.println("Delivery time must be in the future.");
                return ValidatorResult.error("Thời gian chọn phải sau thời gian hiện tại");
            }

        } catch (ParseException e) {
            System.out.println("Invalid delivery time format: " + deliveryTime);
            return ValidatorResult.error("");
        }

        // Gọi phương thức validate tiếp theo (nếu có)
        return validateNext(form);
    }
}
