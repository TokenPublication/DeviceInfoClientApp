## Introduction

This is an example application to demonstrate the usage of DeviceInfo Service. 

## Usage

#### Include  the library in your project

 1) Copy DeviceInfo.aar from /app/libs folder into your project  
 2) Make sure you include aar lib. In this example project, it is done in /app/.build.gradle file like this:

```groovy
dependencies {
    implementation fileTree(dir: "libs", include: ["*.jar", "*.aar"])
    ...
}
```
<br/>

### Reading Device info

Create an instance of DeviceInfo class. It is recommended to reuse this instance when possible instead of creating a new one each time.

```java
DeviceInfo deviceInfo = new DeviceInfo(this);
```

<br/>

#### Method 1: Read one particular field

In order to get a particular field, call related function on deviceInfo object.

```java

deviceInfo.getImeiNumber(new DeviceInfo.DeviceInfoResponseHandler() {
    @Override
    public void onSuccess(String result) {
        imeiNumberText.setText(result);
    }

    @Override
    public void onFail(String errMessage) {
        imeiNumberText.setText(errMessage);
    }
});
```

<br/>

#### Method 2: Using getFields Function
To get multiple fields with a single call, use getFields method.

```java

deviceInfo.getFields(

        fields -> {
            if (fields == null) return;
            // fields is the string array that contains info in the requested order
            Log.i("Example 2", "Fiscal ID:   " + fields[0]);
            Log.i("Example 2", "IMEI Number: " + fields[1]);
            Log.i("Example 2", "IMSI Number: " + fields[2]);
        },

        // write requested fields
        DeviceInfo.Field.FISCAL_ID, DeviceInfo.Field.IMEI_NUMBER, DeviceInfo.Field.IMSI_NUMBER
);
```

## Miscellaneous

### Reading Device Operation Mode

As of the day this document is written, there are 4 operation modes available. They are listed in  DeviceInfo.PosModeEnum class and can have values of: VUK507, POS,GIB, ECR



To get device operation mode you can either use 'Read one particular field' method like this:

```java
// Example 3: Getting mode of Operation
        deviceInfo.getOperationMode(new DeviceInfo.DeviceInfoResponseHandler() {
            @Override
            public void onSuccess(String result) {
                String operationModeStr = result;
                // if you prefer to store it in enum form;
                DeviceInfo.PosModeEnum operationMode = DeviceInfo.PosModeEnum.valueOf(operationModeStr);

                Log.i("Example 3", "getOperationMode returned: " + operationMode);
            }

            @Override
            public void onFail(String errMessage) {
                Log.e("DeviceInfoService", errMessage);
            }
        });
```

or get it via getFields method along with other fields like this:

```java

deviceInfo.getFields(

        fields -> {
            if (fields == null) return;
            // fields is the string array that contains info in the requested order
            Log.i("Example 5", "Fiscal ID:   " + fields[0]);
            Log.i("Example 5", "Operation Mode: " + fields[1]);
            // if you prefer to store it in enum form;
            DeviceInfo.PosModeEnum operationMode = DeviceInfo.PosModeEnum.valueOf(fields[1]);
        },

        // write requested fields
        DeviceInfo.Field.FISCAL_ID, DeviceInfo.Field.OPERATION_MODE
);
```

> It is recommended to store and reuse returned value, because operation mode does not change during runtime.
