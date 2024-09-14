package Controller.Callback;

import Resources.Constants.ToastType;

public interface ToastCallback {
    public void callbackToast(String message, ToastType type);
}
