// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMenuListBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnAddMenu;

  @NonNull
  public final RecyclerView recyclerViewMenu;

  private ActivityMenuListBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnAddMenu,
      @NonNull RecyclerView recyclerViewMenu) {
    this.rootView = rootView;
    this.btnAddMenu = btnAddMenu;
    this.recyclerViewMenu = recyclerViewMenu;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMenuListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMenuListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_menu_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMenuListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAddMenu;
      Button btnAddMenu = ViewBindings.findChildViewById(rootView, id);
      if (btnAddMenu == null) {
        break missingId;
      }

      id = R.id.recyclerViewMenu;
      RecyclerView recyclerViewMenu = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewMenu == null) {
        break missingId;
      }

      return new ActivityMenuListBinding((ConstraintLayout) rootView, btnAddMenu, recyclerViewMenu);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}