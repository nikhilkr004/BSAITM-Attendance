package com.example.bsaitmattendance

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.bsaitmattendance.databinding.ProgressDialogBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Constant {
    private var dialog: AlertDialog? = null
    fun showDialog(context: Context, message: String) {
        val process = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        process.text.text = message.toString()

        dialog = AlertDialog.Builder(context).setView(process.root).setCancelable(true).create()
        dialog!!.show()
    }

    fun hideDialog() {
        dialog!!.dismiss()
    }


    val ref=FirebaseDatabase.getInstance().reference

    fun database(): DatabaseReference {
        return ref.child("Notice").child("Student_Notice")
    }


    val category = arrayOf(
        "java",
        "c-language",
        "cplus",
        "html",
        "python"
    )

    val course = arrayOf(


        "diploma",
        "btech",
    )

    val semester = arrayOf(
        "sem1",
        "sem2",
        "sem3",
        "sem4",
        "sem5",
        "sem6",
        "sem7",
        "sem8"
    )

    val batch = arrayOf(
        "b1",
        "b2",
        "b3"
    )

    val branch = arrayOf(
        "computer",
        "electrical",
        "electronic",
        "civil",
        "mechanical"
    )

    val diplomacomputersem1b1 = arrayOf(
        "English and Communication Skills - I",
        "Applied Mathematics - I",
        "Applied Physics - I",
        "Fundamentals of IT",
        "Computer Workshop"
    )

    val diplomacomputersem2b1 = arrayOf(
        "Advances in IT",
        "Applied Mathematics - II",
        "Applied Physics - II",
        "Analog Electronics",
        "Engineering Graphics",
        "Multimedia Applications",
        "Environmental Studies & Disaster Management"
    )

    val diplomacomputersem3b1 = arrayOf(
        "Industrial/In-House Training - I",
        "Operating Systems",
        "Digital Electronics",
        "Programming in C",
        "Data Base Management System"
    )

    val diplomacomputersem4b1 = arrayOf(
        "English and Communication Skills - II",
        "Computer Organisation & Architecture",
        "Data Structures using C",
        "Object Oriented Programming using Java",
        "Open Elective (MOOCs/Offline)",
        "Minor Project"
    )

    val diplomacomputersem5b1 = arrayOf(
        "Industrial/In-House Training - II",
        "Web Technologies",
        "Python Programming",
        "Computer Networks",
        "Programme Elective - I",
        "Multidisciplinary Elective (MOOCs/Offline)"
    )

    val diplomacomputersem6b1 = arrayOf(
        "Application Development using Web Framework",
        "Entrepreneurship Development & Management",
        "Software Engineering",
        "Programme Elective - II",
        "Major Project/Industrial Training"
    )


    ///////civil

    val diplomacivilsem1b1 = arrayOf(
        "English and Communication Skills - I",
        "Applied Mathematics - I",
        "Applied Physics - I",
        "Applied Chemistry",
        "Engineering Graphics",
        "Plumbing Services",
        "General Workshop Practice"
    )

    val diplomacivilsem2b1 = arrayOf(
        "Fundamentals of IT",
        "Applied Physics - II",
        "Applied Mathematics - II",
        "Civil Engineering Practices",
        "Construction Material",
        "Applied Mechanics",
        "Environmental Studies & Disaster Management"
    )

    val diplomacivilsem3b1 = arrayOf(
        "Industrial/In-House Training - I",
        "Concrete Technology",
        "Structural Mechanics",
        "Surveying - I",
        "Building Construction",
        "Fluid Mechanics",
        "Multidisciplinary Elective"
    )

    val diplomacivilsem4b1 = arrayOf(
        "English & Communication Skills – II",
        "Surveying - II",
        "Water Supply & Waste Water Engineering",
        "Soil Mechanics & Foundation Engineering",
        "Irrigation Engineering",
        "Open Elective",
        "Minor Project"
    )

    val diplomacivilsem5b1 = arrayOf(
        "Industrial/In-House Training - II",
        "RCC Design and Drawing",
        "Estimation & Costing",
        "Railways, Bridges & Tunnels",
        "Highway Engineering",
        "Construction Management",
        "Programme Elective I"
    )

    val diplomacivilsem6b1 = arrayOf(
        "Steel Structures Design & Drawing",
        "Earthquake Resistant Building Construction",
        "Programme Elective II",
        "Major Project/Industrial Training"
    )

//////eletrical
val diplomaelectricalsem1b1 = arrayOf(
    "English & Communication Skills - I",
    "Applied Mathematics - I",
    "Applied Physics - I",
    "Principles of Electrical Engineering",
    "Fundamentals of IT",
    "Engineering Graphics"
)

    val diplomaelectricalsem2b1 = arrayOf(
        "Applied Mathematics - II",
        "Applied Physics - II",
        "Electrical Networks",
        "Non-conventional Sources of Energy",
        "Environmental Studies and Disaster Management",
        "Basic Electrical Workshop"
    )

    val diplomaelectricalsem3b1 = arrayOf(
        "Industrial/In-House Training - I",
        "Electrical Machines - I",
        "Analog & Digital Electronics",
        "Electrical Measurement & Instrumentation",
        "Utilization of Electrical Energy",
        "Power System"
    )

    val diplomaelectricalsem4b1 = arrayOf(
        "English & Communication Skills – II",
        "Electrical Machines - II",
        "Power System Protection",
        "Industrial Electronics and Control of Drives",
        "PLC & Microcontrollers",
        "Minor Project"
    )

    val diplomaelectricalsem5b1 = arrayOf(
        "Industrial/In-House Training - II",
        "Electrical Traction System",
        "HVDC & Flexible AC Transmission Systems",
        "Smart Grid and Distributed Generation System",
        "Energy Conservation and Audit",
        "Entrepreneurship Development and Management"
    )

    val diplomaelectricalsem6b1 = arrayOf(
        "Installation and Maintenance of Electrical Equipment",
        "Solar Panel Installation and Maintenance",
        "Programme Elective II",
        "Major Project/Industrial Training"
    )


    val diplomaelectronicsem1b1 = arrayOf(
        "English and Communication Skills - I",
        "Applied Mathematics - I",
        "Applied Physics - I",
        "Fundamentals of IT",
        "Fundamentals of Electrical Engineering",
        "Electrical & Electronics Workshop - I"
    )

    val diplomaelectronicsem2b1 = arrayOf(
        "Electronic Devices & Circuits - I",
        "Applied Mathematics - II",
        "Applied Physics - II",
        "Electronic Instruments and Measurement",
        "Engineering Graphics",
        "Electrical & Electronics Workshop - II",
        "Environmental Studies & Disaster Management"
    )

    val diplomaelectronicsem3b1 = arrayOf(
        "Industrial/In-House Training - I",
        "Analog and Digital Communication",
        "Digital Electronics",
        "Electronic Devices & Circuits - II",
        "Programming in C",
        "Electronic Design and Simulation"
    )

    val diplomaelectronicsem4b1 = arrayOf(
        "English & Communication Skills – II",
        "Microprocessor & Microcontrollers",
        "Communication Systems/Power Electronics",
        "Instrumentation",
        "PLC & SCADA",
        "Minor Project"
    )

    val diplomaelectronicsem5b1 = arrayOf(
        "Industrial/In-House Training - II",
        "Optical Fibre Communication",
        "Wireless & Mobile Communication",
        "Computer Networks",
        "Programme Elective I"
    )

    val diplomaelectronicsem6b1 = arrayOf(
        "Programme Elective II",
        "Industrial Internship / Major Project"
    )
    val diplomamechanicalsem1b1 = arrayOf(
        "English and Communication Skills - I",
        "Applied Mathematics - I",
        "Applied Physics - I",
        "Engineering Graphics",
        "Fundamentals of IT",
        "Environmental Studies & Disaster Management",
        "General Workshop Practice"
    )

    val diplomamechanicalsem2b1 = arrayOf(
        "Applied Mathematics - II",
        "Applied Physics - II",
        "Applied Chemistry",
        "Applied Mechanics",
        "Mechanical Engineering Drawing - I",
        "Workshop Technology - I",
        "Workshop Practice - I"
    )

    val diplomamechanicalsem3b1 = arrayOf(
        "Industrial/In-House Training - I",
        "Thermodynamics - I",
        "Strength of Materials",
        "Theory of Machines",
        "Mechanical Engineering Drawing - II",
        "Workshop Technology - II",
        "Workshop Practice - II"
    )

    val diplomamechanicalsem4b1 = arrayOf(
        "English & Communication Skills – II",
        "Thermodynamics - II",
        "Fluid Mechanics & Machinery",
        "Manufacturing Processes",
        "Machine Design",
        "Metrology & Quality Control",
        "Minor Project"
    )

    val diplomamechanicalsem5b1 = arrayOf(
        "Industrial/In-House Training - II",
        "Automobile Engineering",
        "Power Plant Engineering",
        "CAD/CAM",
        "Entrepreneurship Development & Management",
        "Programme Elective I"
    )

    val diplomamechanicalsem6b1 = arrayOf(
        "CNC Machines and Automation",
        "Programme Elective II",
        "Industrial Internship / Major Project"
    )

}