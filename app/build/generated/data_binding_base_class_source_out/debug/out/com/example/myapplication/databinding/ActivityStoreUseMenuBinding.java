// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityStoreUseMenuBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnLogout;

  @NonNull
  public final Button btnMenuList;

  @NonNull
  public final FragmentHeaderBinding header;

  @NonNull
  public final LinearLayout linearLayout;

  private ActivityStoreUseMenuBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnLogout,
      @NonNull Button btnMenuList, @NonNull FragmentHeaderBinding header,
      @NonNull LinearLayout linearLayout) {
    this.rootView = rootView;
    this.btnLogout = btnLogout;
    this.btnMenuList = btnMenuList;
    this.header = header;
    this.linearLayout = linearLayout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityStoreUseMenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityStoreUseMenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_store_use_menu, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityStoreUseMenuBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnLogout;
      Button btnLogout = ViewBindings.findChildViewById(rootView, id);
      if (btnLogout == null) {
        break missingId;
      }

      id = R.id.btnMenuList;
      Button btnMenuList = ViewBindings.findChildViewById(rootView, id);
      if (btnMenuList == null) {
        break missingId;
      }

      id = R.id.header;
      View header = ViewBindings.findChildViewById(rootView, id);
      if (header == null) {
        break missingId;
      }
      FragmentHeaderBinding binding_header = FragmentHeaderBinding.bind(header);

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      return new ActivityStoreUseMenuBinding((ConstraintLayout) rootView, btnLogout, btnMenuList,
          binding_header, linearLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
