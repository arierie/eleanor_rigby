import 'package:eleanor_rigby/features/sketch/bloc/sketch_bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class SketchPage extends StatefulWidget {
  const SketchPage({super.key});

  @override
  State<SketchPage> createState() => _SketchPageState();
}

class _SketchPageState extends State<SketchPage> {
  late SketchBloc _sketchBloc;
  final _bodyController = TextEditingController();

  @override
  void initState() {
    _sketchBloc = BlocProvider.of(context);
    super.initState();
  }

  @override
  void dispose() {
    _bodyController.dispose();
    _sketchBloc.close();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: const Text('My sketches')),
        body: _buildBody(_sketchBloc));
  }

  _updateBody() {
    _sketchBloc.add(UpdateBody(_bodyController.text));
  }

  _buildBody(SketchBloc bloc) {
    return BlocBuilder<SketchBloc, SketchState>(
      builder: (BuildContext context, SketchState state) {
        return _body(context, state);
      },
    );
  }

  Widget _body(BuildContext context, SketchState state) {
      return Container(
          color: Colors.white,
          padding: const EdgeInsets.only(
              left: 16, right: 16, top: 8, bottom: 8),
          child: SafeArea(
            left: true,
            right: true,
            top: false,
            bottom: false,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                TextField(
                  decoration: const InputDecoration(
                      border: OutlineInputBorder(), hintText: 'Search ...'),
                  controller: _bodyController,
                  onChanged: _updateBody(),
                ),
                const Divider(
                  height: 16,
                  indent: 16,
                  endIndent: 16,
                  color: Colors.transparent,
                ),
                SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                        onPressed: () {
                          _sketchBloc.add(const GenerateLyricsEvent());
                        },
                        child: const Text("Generate"))),
                const Divider(
                  height: 16,
                  indent: 16,
                  endIndent: 16,
                  color: Colors.transparent,
                ),
                _output(state)
              ],
            ),
          ));
    }

    Widget _output(SketchState state) {
      return Text(state.output);
    }
}
