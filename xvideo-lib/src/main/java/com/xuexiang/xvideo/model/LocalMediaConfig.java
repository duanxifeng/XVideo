/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xvideo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xuexiang
 * @since 2018/5/30 下午8:25
 */
public final class LocalMediaConfig implements Parcelable {

    /**
     * 帧率
     */
    private final int frameRate;

    /**
     * 录制后会剪切一帧缩略图并保存，就是取时间轴上这个时间的画面
     */
    private final int captureThumbnailsTime;

    private final boolean GO_HOME;
    /**
     * 视频压缩配置
     */
    private final MediaCompressConfig compressConfig;

    private final String videoAddress;

    private final float scale;

    private LocalMediaConfig(Builder builder) {
        this.captureThumbnailsTime = builder.captureThumbnailsTime;
        this.frameRate = builder.frameRate;
        this.compressConfig = builder.compressConfig;
        this.videoAddress = builder.videoPath;
        this.scale = builder.scale;
        this.GO_HOME = builder.GO_HOME;

    }


    protected LocalMediaConfig(Parcel in) {
        frameRate = in.readInt();
        captureThumbnailsTime = in.readInt();
        GO_HOME = in.readByte() != 0;
        compressConfig = in.readParcelable(MediaCompressConfig.class.getClassLoader());
        videoAddress = in.readString();
        scale = in.readFloat();
    }

    public static final Creator<LocalMediaConfig> CREATOR = new Creator<LocalMediaConfig>() {
        @Override
        public LocalMediaConfig createFromParcel(Parcel in) {
            return new LocalMediaConfig(in);
        }

        @Override
        public LocalMediaConfig[] newArray(int size) {
            return new LocalMediaConfig[size];
        }
    };

    public boolean isGO_HOME() {
        return GO_HOME;
    }

    public int getCaptureThumbnailsTime() {
        return captureThumbnailsTime;
    }

    public int getFrameRate() {
        return frameRate;
    }


    public MediaCompressConfig getCompressConfig() {
        return compressConfig;
    }

    public String getVideoPath() {
        return videoAddress;
    }

    public float getScale() {
        return scale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(frameRate);
        dest.writeInt(captureThumbnailsTime);
        dest.writeByte((byte) (GO_HOME ? 1 : 0));
        dest.writeParcelable(compressConfig, flags);
        dest.writeString(videoAddress);
        dest.writeFloat(scale);
    }


    public static class Builder {
        /**
         * 录制后会剪切一帧缩略图并保存，就是取时间轴上这个时间的画面
         */
        private int captureThumbnailsTime = 1;


        private boolean GO_HOME = false;

        private MediaCompressConfig compressConfig;
        private int frameRate;

        private String videoPath;
        private float scale;

        public LocalMediaConfig build() {
            return new LocalMediaConfig(this);
        }

        /**
         * @param captureThumbnailsTime 会剪切一帧缩略图并保存，就是取时间轴上这个时间的画面
         * @return
         */
        public Builder captureThumbnailsTime(int captureThumbnailsTime) {
            this.captureThumbnailsTime = captureThumbnailsTime;
            return this;
        }

        /**
         * @param compressConfig 压缩配置设置
         *                       {@link AutoVBRMode }{@link VBRMode}{@link CBRMode}
         * @return
         */
        public Builder doH264Compress(MediaCompressConfig compressConfig) {
            this.compressConfig = compressConfig;
            return this;
        }


        public Builder goHome(boolean GO_HOME) {
            this.GO_HOME = GO_HOME;
            return this;
        }

        public Builder setFramerate(int MAX_FRAME_RATE) {
            this.frameRate = MAX_FRAME_RATE;
            return this;
        }

        public Builder setVideoPath(String videoPath) {
            this.videoPath = videoPath;
            return this;
        }

        /**
         * @param scale 大于1，否者无效
         * @return
         */
        public Builder setScale(float scale) {
            if (scale <= 1) {
                this.scale = 1;
            } else {
                this.scale = scale;
            }
            return this;
        }
    }

}
