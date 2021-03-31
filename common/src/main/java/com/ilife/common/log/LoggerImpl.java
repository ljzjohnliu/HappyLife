package com.ilife.common.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.ilife.common.log.Utils.checkNotNull;

public final class LoggerImpl implements Logger {

  public static final int VERBOSE = 2;
  public static final int DEBUG = 3;
  public static final int INFO = 4;
  public static final int WARN = 5;
  public static final int ERROR = 6;
  public static final int ASSERT = 7;

  private boolean enabled;

  @NonNull private static Printer printer = new LoggerPrinter();

  private LoggerImpl(Builder builder) {
      this.enabled = builder.enabled;
      this.printer = builder.printer == null ? new LoggerPrinter() : builder.printer;
      this.printer.t(builder.tag);
      for (LogAdapter adapter : builder.adapters) {
         addLogAdapter(adapter);
      }
  }

  public void printer(@NonNull Printer printer) {
    LoggerImpl.printer = checkNotNull(printer);
  }

  private void addLogAdapter(@NonNull LogAdapter adapter) {
    printer.addAdapter(checkNotNull(adapter));
  }

  public void clearLogAdapters() {
    printer.clearLogAdapters();
  }

  /**
   * Given tag will be used as tag only once for this method call regardless of the tag that's been
   * set during initialization. After this invocation, the general tag that's been set will
   * be used for the subsequent log calls
   */
  public Printer t(@Nullable String tag) {
    return printer.t(tag);
  }

  /**
   * General log function that accepts all configurations as parameter
   */
  public void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
    printer.log(priority, tag, message, throwable);
  }

  @Override
  public void d(@NonNull String message) {
    printer.d(message, new   Object[]{});
  }

  @Override
  public void e(@NonNull String message, @Nullable Throwable throwable) {
    printer.e(throwable, message, new Object[]{});
  }

  @Override
  public void v(@NonNull String message) {
    printer.v(message, new Object[]{});
  }

  @Override
  public void i(@NonNull String message) {
    printer.i(message, new Object[]{});
  }

  @Override
  public void w(@NonNull String message) {
    printer.w(message, new Object[]{});
  }

  @Override
  public boolean getEnabled() {
    return enabled;
  }

  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public void e(@NonNull String message) {
      printer.e(null, message, new Object[]{});
  }

  public static class Builder {
    private List<LogAdapter> adapters = new ArrayList<>();
    private Printer printer;
    private String tag;
    private boolean enabled;

    public Builder addLogAdapter(LogAdapter a) {
        this.adapters.add(a);
        return this;
    }

    public Builder setPrinter(Printer printer) {
        this.printer = printer;
        return this;
    }

    public Builder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Builder seEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public LoggerImpl build() {
        return new LoggerImpl(this);
    }
  }
}
