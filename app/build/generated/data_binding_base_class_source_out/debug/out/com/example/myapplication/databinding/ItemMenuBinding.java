// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class ItemMenuBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView ivMenuImage;

  @NonNull
  public final TextView tvMenuDescription;

  @NonNull
  public final TextView tvMenuName;

  @NonNull
  public final TextView tvMenuPrice;

  private ItemMenuBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView ivMenuImage,
      @NonNull TextView tvMenuDescription, @NonNull TextView tvMenuName,
      @NonNull TextView tvMenuPrice) {
    this.rootView = rootView;
    this.ivMenuImage = ivMenuImage;
    this.tvMenuDescription = tvMenuDescription;
    this.tvMenuName = tvMenuName;
    this.tvMenuPrice = tvMenuPrice;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemMenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemMenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_menu, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemMenuBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ivMenuImage;
      ImageView ivMenuImage = ViewBindings.findChildViewById(rootView, id);
      if (ivMenuImage == null) {
        break missingId;
      }

      id = R.id.tvMenuDescription;
      TextView tvMenuDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvMenuDescription == null) {
        break missingId;
      }

      id = R.id.tvMenuName;
      TextView tvMenuName = ViewBindings.findChildViewById(rootView, id);
      if (tvMenuName == null) {
        break missingId;
      }

      id = R.id.tvMenuPrice;
      TextView tvMenuPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvMenuPrice == null) {
        break missingId;
      }

      return new ItemMenuBinding((ConstraintLayout) rootView, ivMenuImage, tvMenuDescription,
          tvMenuName, tvMenuPrice);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
