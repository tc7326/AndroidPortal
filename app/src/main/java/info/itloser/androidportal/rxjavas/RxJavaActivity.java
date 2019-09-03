package info.itloser.androidportal.rxjavas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;
import info.itloser.androidportal.rxjavas.bean.Course;
import info.itloser.androidportal.rxjavas.bean.Student;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {
    static final String TAG = "RxJavaActivity";

    @BindView(R.id.iv_test)
    ImageView ivTest;

    Student arts, science;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

        initFakeData();

        networkDemo();

    }

    void createObserver() {

        /*
         * 注册一个观察者
         * */

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "队列已完成");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "队列异常");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "" + s);
            }
        };

        /*
         * 创建一个被观察者[最基础事件序列]
         * */
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("i wanna");
                subscriber.onNext("fuck u");
                subscriber.onCompleted();
            }
        });

        /*
         * 将参数，依次发出
         * */
        observable = Observable.just("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");

        /*
         * 将传入的数组或Iterable拆分成具体对象后以此发出
         * */
        String[] strings = {"壹", "貳", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"};
        observable = Observable.from(strings);

        /*
         * 观察者订阅被观察者[执行这一波任务]
         * */
        observable.subscribe(observer);

    }

    void createSubscriber() {

        /*
         * 和Observer同级的一个操作
         * 但是新增了两个方法：
         * onStart()
         * unSubscribe()
         * */

        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "队列开始");
            }


            @Override
            public void onCompleted() {
                Log.i(TAG, "队列已完成");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "队列异常");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "" + s);
            }
        };
    }

    void createAction() {

        Action1<String> nextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "nextAction-" + s);
            }
        };

        Action1<Throwable> errorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                Log.i(TAG, "errAction-");
            }
        };

        Action0 completedAction = new Action0() {
            @Override
            public void call() {
                Log.i(TAG, "completedAction-");
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });

        observable.subscribe(nextAction);//自定义next

        observable.subscribe(nextAction, errorAction);//自定义next和error

        observable.subscribe(nextAction, errorAction, completedAction);//自定义next，error和complete

    }

    void exampleDemo() {

        //demo0
        String[] strings = {"我", "带", "你", "们", "打"};
        Observable.from(strings).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s);
            }
        });

        //demo1
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(R.drawable.error);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxJavaActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                ivTest.setImageDrawable(drawable);
            }
        });


    }

    void schedulreDemo() {

        //demo0
        Observable.just("1", "2", "3")
                .subscribeOn(Schedulers.io())//指定事件发生的线程
                .observeOn(AndroidSchedulers.mainThread())//指定事件完成后回调的线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "" + s);
                    }
                });

        //demo1
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(R.drawable.error);
//                Toast.makeText(RxJavaActivity.this, "这条Toast将导致奔溃", Toast.LENGTH_SHORT).show();//模拟异常
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())//事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//回调发生在UI线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(RxJavaActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        ivTest.setImageDrawable(drawable);
                    }
                });

    }

    void conversionDemo() {

        //黑芝麻糕，糯米糕，姜汁软糖，椰丝酥，豆沙烙饼，夹心麻薯，酥饺，肚脐酥，花生牛皮糖，麻花，绿豆饼，鸡仔饼，南糖。

        //demo0
        Observable.just(R.drawable.error)
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer drawId) {
                        return BitmapFactory.decodeResource(getResources(), drawId);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        Log.i(TAG, "载入成功");
                        ivTest.setImageBitmap(bitmap);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i(TAG, "载入异常");
                        throwable.printStackTrace();
                    }
                });

        //get fake data

        Student[] students = {arts, science};

        //demo1

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };

        Observable.from(students).map(new Func1<Student, String>() {
            @Override
            public String call(Student student) {
                return student.getName();
            }
        }).subscribe(subscriber);

        //demo2

        Subscriber<Student> studentSubscriber = new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                List<Course> courses = student.getCourses();
                for (Course c : courses) {
                    Log.i(TAG, student.getId() + student.getName() + c.getName());
                }
            }
        };

        Observable.from(students).subscribe(studentSubscriber);

        //demo3

        Subscriber<Course> courseSubscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.i(TAG, course.getName());
            }
        };

        Observable.from(students).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                return Observable.from(student.getCourses());
            }
        }).subscribe(courseSubscriber);

    }

    void networkDemo() {
        

    }


    void initFakeData() {
        Course chinese = new Course(0, "语文");
        Course math = new Course(1, "数学");
        Course english = new Course(2, "英语");
        Course chemistry = new Course(3, "化学");
        Course history = new Course(4, "历史");

        List<Course> artsCourse = new ArrayList<>();
        artsCourse.add(chinese);
        artsCourse.add(math);
        artsCourse.add(english);
        artsCourse.add(history);

        List<Course> scienceCourse = new ArrayList<>();
        scienceCourse.add(chinese);
        scienceCourse.add(math);
        scienceCourse.add(english);
        scienceCourse.add(chemistry);

        arts = new Student(0, "文科生", artsCourse);
        science = new Student(1, "理科生", scienceCourse);
    }


}
