package com.imooc.house.api.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class Test {

    public static void main(String[] args){
        Employee e1 = new Employee(1,23,"M","Rick","Beethovan");
        Employee e2 = new Employee(2,13,"F","Martina","Hengis");
        Employee e3 = new Employee(3,43,"M","Ricky","Martin");
        Employee e4 = new Employee(4,26,"M","Jon","Lowman");
        Employee e5 = new Employee(5,19,"F","Cristine","Maria");
        Employee e6 = new Employee(6,15,"M","David","Feezor");
        Employee e7 = new Employee(7,68,"F","Melissa","Roy");
        Employee e8 = new Employee(8,79,"M","Alex","Gussin");
        Employee e9 = new Employee(9,15,"F","Neetu","Singh");
        Employee e10 = new Employee(10,45,"M","Naveen","Jain");

        List<Employee> employees = new ArrayList<Employee>();
        employees.addAll(Arrays.asList(new Employee[]{e1,e2,e3,e4,e5,e6,e7,e8,e9,e10}));
//        List<Employee> list = filterEmployees(employees, isAdultMale());
//        for(Employee e : list){
//            System.out.println(e.getFirstName());
//            System.out.println(e.getLastName());
//        }
//        System.out.println(list);
//
//        System.out.println(filterEmployees(employees, isAdultFemale()));
//
//        System.out.println(filterEmployees(employees, isAgeMoreThan(35)));

        //Employees other than above collection of "isAgeMoreThan(35)" can be get using negate()
//        System.out.println(filterEmployees(employees, isAgeMoreThan(35).negate()));

//        Predicate<Employee> predicate = e->e.getAge()>60;
//        List<Employee> list = filterEmployees(employees, predicate);
//        System.out.println(list.size());
//        for(Employee e : list){
//            System.out.println(e.getLastName());
//            System.out.println(e.getFirstName());
//            System.out.println(e.getAge());
//            System.out.println(e.getGender());
//            System.out.println(e.getId());
//        }

//        BinaryOperator<String> binaryOperator = (a,b)-> a.length()-b.length() ;
//          BinaryOperator.minBy();

//        BinaryOperator<String> binaryOperator = (a, b) -> a.length() - b.length() + "";
//        String str = binaryOperator.apply("helloworld","hehe.com");
//        System.out.println(binaryOperator.apply("helloworld","hehe.com"));
//        String joined = employees.stream()
//                .map(Employee::getFirstName)
//                .collect(Collectors.joining(","));
//        System.out.println(joined);
//        Set<String> employeeSet = employees.stream().map(Employee::getFirstName).collect(Collectors.toCollection(TreeSet::new));
//        String joinFirstName = employeeSet.stream().collect(Collectors.joining(","));
//        System.out.println(joinFirstName);
//        Integer ageTotal = employees.stream().collect(Collectors.summingInt(Employee::getAge));
//        System.out.println(ageTotal);
//
//        Map<String, List<Employee>> employeeMap = employees.stream().collect(Collectors.groupingBy(Employee::getGender));
//        employeeMap.forEach((k,v)->{
//            System.out.println(k);
//            v.forEach(e->{
//                System.out.println(e.getId());
//                System.out.println(e.getGender());
//                System.out.println(e.getFirstName());
//            });
//            System.out.println("---------------------------------------");
//        });

//

        //定义一个function 输入是String类型，输出是 EventInfo 类型，  EventInfo是一个类。
//        Function<String, Employee> times2 = fun -> { Employee a = new Employee(); a.setId(100);a.setAge(18); return a;};
//
//        String[] testintStrings={"1","2","3","4"};

        //将String 的Array转换成map,调用times2函数进行转换
//        Map<String,Employee> eventmap3= Stream.of(testintStrings)
//                .collect(Collectors
//                        .toMap(inputvalue->inputvalue, inputvalue->
//                        { Employee a = new Employee(); a.setId(100);a.setAge(18); return a;}));
//
//        Map<String,Employee> eventmap2 = Stream.of("1","2","3","4")
//                .collect(Collectors.toMap(inputvalue -> inputvalue, inputvalue -> getEmployee()));


//        Stream.of("1","2","3","4").forEach(System.out::println);
//        eventmap2.forEach(System.out::println);

//        Map<String,String> eventmap2=Stream.of(testintStrings).collect(Collectors.toMap(inputvalue->inputvalue, inputvalue->(inputvalue+"a")));
//        eventmap2.forEach((k,v)->{
//            System.out.println(k+"-->"+v);
//        });

       /* long count = employees.stream()
                .filter(isAgeMoreThan(20))
                .count();*/
//        System.out.println(count);/**/

//        Employee shortestTrack = employees.stream()
//                .min(Comparator.comparing(track -> track.getAge()))
//                .get();
//        Employee shortestTrack = employees.stream()
//                .max(Comparator.comparing(tracke->tracke.getAge()))
//                .get();
//        System.out.println(shortestTrack);
        int counts = Stream.of(1,2,3,4,5,6)
                .reduce(1,(a,b)->a*b);

        System.out.println(counts);




    }





}
