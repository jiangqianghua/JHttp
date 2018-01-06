# JHttp
自己封装底层Http框架

  1） 使用方式
        Request request = new Request(Constants.UR_UPOAD_POST);
        request.add("userName","jiang");
        request.add("passWord","150700");
        RequestExecutor.INTANCE.execute(request, new HttpListener() {
            @Override
            public void onSuccessed(Response response) {
                Logger.i("结果："+response.getResult());
            }

            @Override
            public void onFailed(Exception e) {
                Logger.i("结果："+e.getMessage());
            }
        });
