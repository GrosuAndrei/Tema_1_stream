package homework_labs1;

import java.lang.Object;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDate.*;
import java.util.stream.*;

class Customer {
	private Long id;
	private String name;
	private Integer tier;
	public Customer(Long id, String name, Integer tier)
	{
		this.id=id;
		this.name=name;
		this.tier=tier;
	}
	
	public Integer getTier()
	{
		return tier;
	}
}

class Order {
	private Long id;
	private LocalDate orderDate;
	private LocalDate deliveryDate;
	private String status;
	private Customer customer;
	private Set<Product> products;
	
	public Order(Long id,LocalDate orderDate,LocalDate deliveryDate,String status,
			Customer customer,Set<Product> products)
	{
		this.customer=customer;
		this.deliveryDate=deliveryDate;
		this.id=id;
		this.orderDate=orderDate;
		this.products=products;
		this.status=status;
	}
	
	public Set<Product> getProducts()
	{
		return products;
	}
	
	public String toString()
	{
		return " id: " + id + " products: " + products + " orderDate: "+ orderDate;
	}
	
	public Customer getCustomer()
	{
		return customer;
	}
	
	public LocalDate getOrderDate()
	{
		return orderDate;
	}
	
	public Double getTotalPrice()
	{
		return products.stream()
				.map(x->x.getPrice())
				.reduce(0.00,Double::sum);
	}
	
	public Double getAvg()
	{
		return products.stream()
				.collect(Collectors.averagingDouble(Product::getPrice));
	}
	
}

class Product {
	private Long id;
	private String name;
	private String category;
	private Double price;
	private Long counter=0L;
	
	public Product(Long id,String name,String category,Double price)
	{
		this.category=category;
		this.id=id;
		this.name=name;
		this.price=price;
	}
	
	public Double getPrice()
	{
		return price;
	}
	
	public String toString()
	{
		String s="";
		return s+" id: " + id + " name: " + name + " category: " + category
				+ " price: " + price + " ";
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public Product discount(Product p)
	{
		p.price=this.price-p.price*0.1;
		return p;
	}
	
	public Product getMostExpensive(String category, List<Product> productList)
	{
		Product p=productList.stream()
				.filter(x->x.getCategory().equals(category))
				.sorted((x1,x2)->x2.getPrice().compareTo(x1.getPrice()))
				.findFirst()
				.orElse(null);
		return p;
	}
	
	public Long getCounter()
	{
		return  counter;
	}
	
	public Product numara(Product p)
	{
		p.counter=p.counter+1;
		return p;
	}
}


public class Main {
	
	public static void main(String[] args)
	{
		List<Customer> customerList=new ArrayList<Customer>();
		List<Order> orderList=new ArrayList<Order>();
		List<Product> productList=new ArrayList<Product>();
	
		Product p1=new Product(10L,"Blur","Books",100.00);
		Product p2=new Product(11L,"Reventant","Books",120.00);
		Product p3=new Product(12L,"Pig","Toys",20.00);
		Product p4=new Product(13L,"Fallen Sky","Books",200.00);
		Product p5=new Product(14L,"pacifier","Baby",10.00);
		Product p6=new Product(15L,"Castle","Toys",1000.00);
		
		Customer c1=new Customer(20L,"Jonny",1);
		Customer c2=new Customer(21L,"Alice",2);
		Customer c3=new Customer(22L,"Johan",2);
		
		Set<Product> s1=new HashSet<Product>();
		s1.add(p1);
		s1.add(p4);
		
		Set<Product> s2=new HashSet<Product>();
		s2.add(p1);
		s2.add(p4);
		s2.add(p5);
		s2.add(p6);
		
		Set<Product> s3=new HashSet<Product>();
		s3.add(p2);
		s3.add(p3);
		s3.add(p1);
		s3.add(p5);
		s3.add(p6);
		
		LocalDate d1=LocalDate.parse("2021-03-14");
		LocalDate d2=LocalDate.parse("2021-03-24");
		LocalDate d3=LocalDate.parse("2021-02-21");
		
		Order o1=new Order(30L,d1,d2,"received",c1,s1);
		Order o2=new Order(31L,d3,d1,"delivered",c2,s2);
		Order o3=new Order(32L,d3,d2,"in progress",c3,s3);
		
		customerList.add(c1);
		customerList.add(c2);
		customerList.add(c3);
		
		orderList.add(o1);
		orderList.add(o2);
		orderList.add(o3);
		
		productList.add(p1);
		productList.add(p2);
		productList.add(p3);
		productList.add(p4);
		productList.add(p5);
		productList.add(p6);
		
		//ex1
		List<Product>productL1=productList.stream()
				.filter(x->x.getPrice()>100.00)
				.collect(Collectors.toList());
		
		//System.out.println(productL1);
		
		//ex2
		
		List<Order>orderL1=orderList.stream()
				.filter(x->x.getProducts().stream()
						.anyMatch(i->i.getCategory().equals("Baby")))
						.collect(Collectors.toList());
		
		//System.out.println(orderL1);
		
		//ex3
		productL1=productList.stream()
				.filter(x->x.getCategory().equals("Toys"))
				.map(i->i.discount(i))
				.collect(Collectors.toList());
		//System.out.println(productL1);
		
		//ex4
		
		LocalDate Start=LocalDate.parse("2021-02-01");
		LocalDate End= LocalDate.parse("2021-04-01");

		List<Set<Product>> l1=orderList.stream()
				.filter(x->x.getCustomer().getTier()==2)
				.filter(x->(x.getOrderDate().isAfter(Start) && x.getOrderDate().isBefore(End)))
				.map(x->x.getProducts())
				.collect(Collectors.toList());
		
		//System.out.println(l1);

		//ex5
		
		Product prod1=productList.stream()
				.filter(x->x.getCategory().equals("Books"))
				.sorted((x1,x2)->x1.getPrice().compareTo(x2.getPrice()))
				.findFirst()
				.orElse(null);
		
		//System.out.println(prod1);
		
		//ex6
		
		orderL1=orderList.stream()
				.sorted((x1,x2)->x1.getOrderDate().compareTo(x2.getOrderDate()))
				.limit(3)
				.collect(Collectors.toList());
		
		//System.out.println(orderL1);
		
		//ex7
		
		Order order1=orderList.stream()
				.sorted((x1,x2)->x2.getTotalPrice().compareTo(x1.getTotalPrice()))
				.findFirst()
				.orElse(null);
		
		//System.out.println(order1);
		
		//ex8
				
		order1=orderList.stream()
				.filter(x->x.getOrderDate().isEqual(d1))
				.findFirst()
				.orElse(null);
		
		//System.out.println(order1.getAvg());
		
		//ex9
		
		prod1=prod1.getMostExpensive("Books", productList);
		//System.out.println(prod1);
		
		//ex10
		
		productL1=orderList.stream()
				.map(x->x.getProducts().stream()
						.map(y->y.numara(y))
						.sorted((x1,x2)->x2.getCounter().compareTo(x1.getCounter()))
						.findFirst()
						.orElse(null))
				.collect(Collectors.toList());

		prod1=productL1.stream()
				.sorted((x1,x2)->x2.getCounter().compareTo(x1.getCounter()))
				.findFirst()
				.orElse(null);
		System.out.println(prod1);
	}
}
