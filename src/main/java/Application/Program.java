package Application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Task", "root", "root");

		Scanner sc = new Scanner(System.in);

		boolean flag = true;
		while (flag == true) {
			System.out.println("1.add details");
			System.out.println("2.fetch details based upon date");
			System.out.println("3.update the date");
			System.out.println("4.update the image");
			System.out.println("5.remove the details based upon date");
			System.out.println("6.exit");
			System.out.println("enter the valid choice");
			int opt = sc.nextInt();
			switch (opt) {
			case 1: {
				System.out.println("enter the id");
				int id = sc.nextInt();
				System.out.println("enter the name");
				String name = sc.next();
				System.out.println("enter the topic name");
				String topic_name = sc.next();
				System.out.println("enter the date");
				String date = sc.next();
				System.out.println("enter the start time");
				String start_time = sc.next();
				System.out.println("enter the end time");
				String end_time = sc.next();
				System.out.println("enter the image path that u want to insert the image");
				String photo = sc.next();
				System.out.println("enter the seal image path that u want to insert");
				String seal = sc.next();

				PreparedStatement pt = con.prepareStatement("insert into presentationtracking values(?,?,?,?,?,?,?,?)");
				pt.setInt(1, id);
				pt.setString(2, name);
				pt.setString(3, topic_name);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				java.util.Date udate = sdf.parse(date);
				long l = udate.getTime();
				java.sql.Date sdate = new java.sql.Date(l);
				pt.setDate(4, sdate);

				SimpleDateFormat sdf1 = new SimpleDateFormat("hh-mm-ss");
				java.util.Date udate1 = sdf1.parse(start_time);
				long l1 = udate1.getTime();
				Time t = new Time(l1);
				pt.setTime(5, t);

				SimpleDateFormat sdf2 = new SimpleDateFormat("hh-mm-ss");
				java.util.Date udate2 = sdf2.parse(end_time);
				long l2 = udate2.getTime();
				Time t1 = new Time(l2);
				pt.setTime(6, t1);

				FileInputStream f = new FileInputStream(photo);
				pt.setBinaryStream(7, f, f.available());

				FileInputStream f1 = new FileInputStream(seal);
				pt.setBinaryStream(8, f1, f1.available());

				pt.executeUpdate();
			}
				System.out.println("addedd.......");
				break;
			case 2: {
				System.out.println("enter the date");
				String date1 = sc.next();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				java.util.Date udate = sdf.parse(date1);
				long l = udate.getTime();
				java.sql.Date sdate = new java.sql.Date(l);
				PreparedStatement pt = con.prepareStatement("select * from presentationtracking where date=?");
				pt.setDate(1, sdate);
				ResultSet e = pt.executeQuery();
				while (e.next()) {
					System.out.println(e.getInt(1));
					System.out.println(e.getString(2));
					System.out.println(e.getString(3));
					System.out.println(e.getDate(4));
					System.out.println(e.getTime(5));
					System.out.println(e.getTime(6));
					FileOutputStream f1 = new FileOutputStream("C:\\Users\\dell\\Desktop\\get\\p1.jpg");
					Blob b = e.getBlob(7);
					f1.write(b.getBytes(1, (int) b.length()));
					FileOutputStream f2 = new FileOutputStream("C:\\Users\\dell\\Desktop\\get\\i1.jpg");
					Blob b1 = e.getBlob(8);
					f2.write(b.getBytes(1, (int) b1.length()));
				}
			}
				System.out.println("details are fetched.................");

				break;
			case 3: {
				System.out.println("enter the date");
				String date = sc.next();
				System.out.println("enter the id");
				int id= sc.nextInt();
				PreparedStatement pt = con.prepareStatement("update presentationtracking set date=? where id=?");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				java.util.Date udate = sdf.parse(date);
				long l = udate.getTime();
				java.sql.Date sdate = new java.sql.Date(l);
				pt.setDate(1, sdate);
				pt.setInt(2, id);
				pt.executeUpdate();
			}
				System.out.println("updated.................");
				break;
			case 4: {
				System.out.println("insert image path that u want to add the image");
				String pic = sc.next();
				System.out.println("enter the id");
				int id = sc.nextInt();
				PreparedStatement pt = con.prepareStatement("update presentationtracking set photo=? where id=?");
				FileInputStream f = new FileInputStream(pic);
				pt.setBinaryStream(1, f, f.available());
				pt.setInt(2, id);
				pt.executeUpdate();
			}

				System.out.println("image updated...............");
				break;
			case 5: {
				System.out.println("enter the date");
				String date = sc.next();
				PreparedStatement pt = con.prepareStatement("delete from presentationtracking where date=?");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				java.util.Date udate = sdf.parse(date);
				long l = udate.getTime();
				java.sql.Date sdate = new java.sql.Date(l);
				pt.setDate(1, sdate);
				pt.executeUpdate();

				System.out.println("removed.............");
			}
				break;
			case 6: {
				flag = false;
				System.out.println("thank you for using this application");
			}

				break;

			default:
				System.out.println("invalid choice");
				break;
			}

		}
		con.close();

	}

}
