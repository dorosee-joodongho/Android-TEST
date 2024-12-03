// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityStoreOrderBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RecyclerView storeOrderRecyclerView;

  @NonNull
  public final ImageView userImage;

  @NonNull
  public final TextView userName;

  private ActivityStoreOrderBinding(@NonNull LinearLayout rootView,
      @NonNull RecyclerView storeOrderRecyclerView, @NonNull ImageView userImage,
      @NonNull TextView userName) {
    this.rootView = rootView;
    this.storeOrderRecyclerView = storeOrderRecyclerView;
    this.userImage = userImage;
    this.userName = userName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityStoreOrderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityStoreOrderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_store_order, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityStoreOrderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.storeOrderRecyclerView;
      RecyclerView storeOrderRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (storeOrderRecyclerView == null) {
        break missingId;
      }

      id = R.id.userImage;
      ImageView userImage = ViewBindings.findChildViewById(rootView, id);
      if (userImage == null) {
        break missingId;
      }

      id = R.id.userName;
      TextView userName = ViewBindings.findChildViewById(rootView, id);
      if (userName == null) {
        break missingId;
      }

      return new ActivityStoreOrderBinding((LinearLayout) rootView, storeOrderRecyclerView,
          userImage, userName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}