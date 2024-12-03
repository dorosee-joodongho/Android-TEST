// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentStoreOrderDetailBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button cancelOrderButton;

  @NonNull
  public final TextView customerNameTextView;

  @NonNull
  public final TextView customerPhoneNumberTextView;

  @NonNull
  public final TableLayout menuItemsTableLayout;

  @NonNull
  public final TextView menuItemsTextView;

  @NonNull
  public final TextView orderDateTextView;

  @NonNull
  public final TextView orderDetailTitleTextView;

  @NonNull
  public final TextView storeOrderStatusTextView;

  @NonNull
  public final TextView totalPaymentAmountTextView;

  @NonNull
  public final TextView totalPaymentTextView;

  private FragmentStoreOrderDetailBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button cancelOrderButton, @NonNull TextView customerNameTextView,
      @NonNull TextView customerPhoneNumberTextView, @NonNull TableLayout menuItemsTableLayout,
      @NonNull TextView menuItemsTextView, @NonNull TextView orderDateTextView,
      @NonNull TextView orderDetailTitleTextView, @NonNull TextView storeOrderStatusTextView,
      @NonNull TextView totalPaymentAmountTextView, @NonNull TextView totalPaymentTextView) {
    this.rootView = rootView;
    this.cancelOrderButton = cancelOrderButton;
    this.customerNameTextView = customerNameTextView;
    this.customerPhoneNumberTextView = customerPhoneNumberTextView;
    this.menuItemsTableLayout = menuItemsTableLayout;
    this.menuItemsTextView = menuItemsTextView;
    this.orderDateTextView = orderDateTextView;
    this.orderDetailTitleTextView = orderDetailTitleTextView;
    this.storeOrderStatusTextView = storeOrderStatusTextView;
    this.totalPaymentAmountTextView = totalPaymentAmountTextView;
    this.totalPaymentTextView = totalPaymentTextView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentStoreOrderDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentStoreOrderDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_store_order_detail, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentStoreOrderDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cancelOrderButton;
      Button cancelOrderButton = ViewBindings.findChildViewById(rootView, id);
      if (cancelOrderButton == null) {
        break missingId;
      }

      id = R.id.customerNameTextView;
      TextView customerNameTextView = ViewBindings.findChildViewById(rootView, id);
      if (customerNameTextView == null) {
        break missingId;
      }

      id = R.id.customerPhoneNumberTextView;
      TextView customerPhoneNumberTextView = ViewBindings.findChildViewById(rootView, id);
      if (customerPhoneNumberTextView == null) {
        break missingId;
      }

      id = R.id.menuItemsTableLayout;
      TableLayout menuItemsTableLayout = ViewBindings.findChildViewById(rootView, id);
      if (menuItemsTableLayout == null) {
        break missingId;
      }

      id = R.id.menuItemsTextView;
      TextView menuItemsTextView = ViewBindings.findChildViewById(rootView, id);
      if (menuItemsTextView == null) {
        break missingId;
      }

      id = R.id.orderDateTextView;
      TextView orderDateTextView = ViewBindings.findChildViewById(rootView, id);
      if (orderDateTextView == null) {
        break missingId;
      }

      id = R.id.orderDetailTitleTextView;
      TextView orderDetailTitleTextView = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailTitleTextView == null) {
        break missingId;
      }

      id = R.id.storeOrderStatusTextView;
      TextView storeOrderStatusTextView = ViewBindings.findChildViewById(rootView, id);
      if (storeOrderStatusTextView == null) {
        break missingId;
      }

      id = R.id.totalPaymentAmountTextView;
      TextView totalPaymentAmountTextView = ViewBindings.findChildViewById(rootView, id);
      if (totalPaymentAmountTextView == null) {
        break missingId;
      }

      id = R.id.totalPaymentTextView;
      TextView totalPaymentTextView = ViewBindings.findChildViewById(rootView, id);
      if (totalPaymentTextView == null) {
        break missingId;
      }

      return new FragmentStoreOrderDetailBinding((ConstraintLayout) rootView, cancelOrderButton,
          customerNameTextView, customerPhoneNumberTextView, menuItemsTableLayout,
          menuItemsTextView, orderDateTextView, orderDetailTitleTextView, storeOrderStatusTextView,
          totalPaymentAmountTextView, totalPaymentTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}