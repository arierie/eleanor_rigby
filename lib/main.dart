import 'package:eleanor_rigby/features/sketch/bloc/sketch_bloc.dart';
import 'package:eleanor_rigby/features/sketch/sketch_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider<SketchBloc>(
          create: (context) => SketchBloc(),
        ),
      ],
      child: MaterialApp(
        title: 'Eleanor Rigby',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.white),
          useMaterial3: true,
        ),
        home: const SketchPage(),
      ),
    );
  }
}
