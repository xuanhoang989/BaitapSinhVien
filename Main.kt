import java.util.Scanner

data class Student(
    val id: String,
    var fullName: String,
    var age: Int,
    var major: String,
    var gpa: Double
) {

    val firstName: String
        get() = fullName.trim().split("\\s+".toRegex()).lastOrNull() ?: fullName

    override fun toString(): String {
        return "| %-10s | %-22s | %-4d | %-20s | %-5.2f |".format(id, fullName, age, major, gpa)
    }
}

class StudentManager {
    // 5 sinh viên mẫu độc lập
    val students = mutableListOf(
        Student("SV001", "Nguyễn Văn Đạt", 20, "Công nghệ thông tin", 8.4),
        Student("SV002", "Trần Thị Mai Anh", 21, "Kỹ thuật phần mềm", 7.2),
        Student("SV003", "Lê Hoàng Phúc", 22, "Công nghệ thông tin", 9.1),
        Student("SV004", "Phạm Quốc Tuấn", 19, "Hệ thống thông tin", 4.6),
        Student("SV005", "Võ Minh Khôi", 23, "An toàn thông tin", 6.8)
    )

    private fun printHeader() {
        println("+------------+------------------------+------+----------------------+-------+")
        println("| ID         | Full Name              | Age  | Major                | GPA   |")
        println("+------------+------------------------+------+----------------------+-------+")
    }

    private fun printFooter() {
        println("+------------+------------------------+------+----------------------+-------+")
    }

    fun displayList(list: List<Student> = students) {
        if (list.isEmpty()) {
            println("Danh sách trống!")
            return
        }
        printHeader()
        list.forEach { println(it) }
        printFooter()
    }

    fun addStudent(scanner: Scanner) {
        print("Nhập Student ID: ")
        val id = scanner.nextLine().trim()
        if (students.any { it.id.equals(id, ignoreCase = true) }) {
            println("Lỗi: Mã sinh viên '$id' đã tồn tại!")
            return
        }

        print("Nhập Full Name: ")
        val name = scanner.nextLine().trim()

        print("Nhập Age: ")
        val age = scanner.nextLine().toIntOrNull() ?: run {
            println("Tuổi không hợp lệ!")
            return
        }

        print("Nhập Major: ")
        val major = scanner.nextLine().trim()

        print("Nhập GPA (0.0 - 10.0): ")
        val gpa = scanner.nextLine().toDoubleOrNull() ?: run {
            println("GPA không hợp lệ!")
            return
        }

        students.add(Student(id, name, age, major, gpa))
        println("Thêm sinh viên thành công!")
    }

    fun searchStudent(scanner: Scanner) {
        print("Nhập ID hoặc một phần tên cần tìm: ")
        val keyword = scanner.nextLine().trim().lowercase()
        val results = students.filter {
            it.id.lowercase().contains(keyword) || it.fullName.lowercase().contains(keyword)
        }
        println("Kết quả tìm kiếm cho '$keyword':")
        displayList(results)
    }

    fun removeStudent(scanner: Scanner) {
        print("Nhập Student ID cần xóa: ")
        val id = scanner.nextLine().trim()
        val removed = students.removeIf { it.id.equals(id, ignoreCase = true) }
        if (removed) {
            println("Đã xóa sinh viên có ID: $id")
        } else {
            println("Không tìm thấy sinh viên có ID: $id")
        }
    }

    fun calculateAverageGpa() {
        if (students.isEmpty()) {
            println("Danh sách trống, không thể tính GPA trung bình.")
            return
        }
        val avg = students.map { it.gpa }.average()
        println("GPA trung bình của toàn bộ sinh viên: %.2f".format(avg))
    }

    fun findHighestGpa() {
        val top = students.maxByOrNull { it.gpa }
        if (top != null) {
            println("Sinh viên có GPA cao nhất:")
            displayList(listOf(top))
        } else {
            println("Danh sách trống!")
        }
    }

    fun runExtraRequirements(scanner: Scanner) {
        while (true) {
            println("\n-------------- CÁC CHỨC NĂNG NÂNG CAO --------------")
            println("1. Đếm sinh viên có GPA >= 8.0")
            println("2. Đếm sinh viên có GPA < 5.0")
            println("3. Tính GPA trung bình theo một ngành cụ thể")
            println("4. Tìm sinh viên lớn tuổi nhất")
            println("5. Lọc sinh viên có GPA trong khoảng 7.0 -> 8.5")
            println("6. Tìm tất cả sinh viên thuộc một ngành")
            println("7. Sắp xếp sinh viên theo GPA giảm dần")
            println("8. Hiển thị 3 sinh viên có GPA cao nhất")
            println("9. Sắp xếp sinh viên theo tuổi (tăng dần)")
            println("10. Sắp xếp sinh viên theo tên (A-Z)")
            println("0. Quay lại Menu chính")
            print("Chọn chức năng: ")

            when (scanner.nextLine().trim()) {
                "1" -> {
                    val count = students.count { it.gpa >= 8.0 }
                    println("Số sinh viên có GPA >= 8.0: $count")
                }
                "2" -> {
                    val count = students.count { it.gpa < 5.0 }
                    println("Số sinh viên có GPA < 5.0: $count")
                }
                "3" -> {
                    print("Nhập tên ngành cần tính GPA trung bình: ")
                    val major = scanner.nextLine().trim()
                    val filtered = students.filter { it.major.equals(major, ignoreCase = true) }
                    if (filtered.isEmpty()) {
                        println("Không có sinh viên nào thuộc ngành '$major'.")
                    } else {
                        val avg = filtered.map { it.gpa }.average()
                        println("GPA trung bình ngành '$major': %.2f (Dựa trên ${filtered.size} sinh viên)".format(avg))
                    }
                }
                "4" -> {
                    val oldest = students.maxByOrNull { it.age }
                    if (oldest != null) {
                        println("Sinh viên lớn tuổi nhất:")
                        displayList(listOf(oldest))
                    } else {
                        println("Danh sách trống!")
                    }
                }
                "5" -> {
                    val inRange = students.filter { it.gpa in 7.0..8.5 }
                    println("Danh sách sinh viên có GPA từ 7.0 đến 8.5:")
                    displayList(inRange)
                }
                "6" -> {
                    print("Nhập tên ngành cần lọc: ")
                    val major = scanner.nextLine().trim()
                    val inMajor = students.filter { it.major.contains(major, ignoreCase = true) }
                    displayList(inMajor)
                }
                "7" -> {
                    val sorted = students.sortedByDescending { it.gpa }
                    println("Danh sách sau khi sắp xếp theo GPA giảm dần:")
                    displayList(sorted)
                }
                "8" -> {
                    val top3 = students.sortedByDescending { it.gpa }.take(3)
                    println("Top 3 sinh viên có GPA cao nhất:")
                    displayList(top3)
                }
                "9" -> {
                    val sorted = students.sortedBy { it.age }
                    println("Danh sách sắp xếp theo độ tuổi tăng dần:")
                    displayList(sorted)
                }
                "10" -> {
                    val sorted = students.sortedBy { it.firstName }
                    println("Danh sách sắp xếp theo Tên (A-Z):")
                    displayList(sorted)
                }
                "0" -> return
                else -> println("Lựa chọn không hợp lệ, vui lòng thử lại.")
            }
        }
    }
}

fun main() {
    val manager = StudentManager()
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\n========== STUDENT MANAGEMENT ==========")
        println("1. Add student")
        println("2. Display all students")
        println("3. Search student")
        println("4. Calculate average GPA")
        println("5. Find student with highest GPA")
        println("6. Remove student")
        println("7. Advanced Operations (Đầy đủ theo yêu cầu chi tiết)")
        println("0. Exit")
        println("========================================")
        print("Choose: ")

        when (scanner.nextLine().trim()) {
            "1" -> manager.addStudent(scanner)
            "2" -> manager.displayList()
            "3" -> manager.searchStudent(scanner)
            "4" -> manager.calculateAverageGpa()
            "5" -> manager.findHighestGpa()
            "6" -> manager.removeStudent(scanner)
            "7" -> manager.runExtraRequirements(scanner)
            "0" -> {
                println("Thoát chương trình. Tạm biệt!")
                break
            }
            else -> println("Lựa chọn không hợp lệ! Vui lòng chọn lại.")
        }
    }
}